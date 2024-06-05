package com.augustodev.kiraserver.modules.tasks;

import com.augustodev.kiraserver.common.exceptions.BadRequestException;
import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundException;
import com.augustodev.kiraserver.modules.boards.BoardService;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import com.augustodev.kiraserver.modules.status.StatusService;
import com.augustodev.kiraserver.modules.status.entities.Status;
import com.augustodev.kiraserver.modules.tasks.dtos.request.AssignTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.request.ChangeTaskStatusDto;
import com.augustodev.kiraserver.modules.tasks.dtos.request.CreateTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.response.TasksResponseSlimDto;
import com.augustodev.kiraserver.modules.tasks.entities.Task;
import com.augustodev.kiraserver.modules.tasks.mapper.TaskMapper;
import com.augustodev.kiraserver.modules.users.UserService;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;
    private final StatusService statusService;
    private final BoardService boardService;
    private final UserService userService;
    private final TaskMapper taskMapper;

    public Task findTaskByIdElseThrow(Integer taskId) {
        Optional<Task> taskOpt = this.tasksRepository.findById(taskId);

        if (taskOpt.isEmpty()) {
            throw new ResourceNotFoundException("Task with this id not found!");
        }

        return taskOpt.get();
    }

    public List<Task> findTasksByStatusIdElseThrow(Integer statusId) {
        List<Task> tasks = this.tasksRepository.findByStatusId(statusId);

        if (tasks.isEmpty()) {
            throw new ResourceNotFoundException("Task with this id not found!");
        }

        return tasks;
    }

    public TasksResponseSlimDto create(CreateTaskDto createTaskDto, Integer boardId, User user) {
        Board board = this.boardService.findBoardByIdElseThrow(boardId);

        BoardMembers boardMember = this.boardService.findUserAssociatedBoardElseThrow(board.getId(), user.getId());

        this.boardService.isBoardMemberBoardAdminElseThrow(boardMember);

        Status status = this.statusService.findStatusByIdElseThrow(createTaskDto.getStatus_id());

        if (!Objects.equals(board.getId(), status.getBoard().getId())) {
            throw new BadRequestException("Status does not exists in this board!");
        }

        Task task = Task.builder()
                .title(createTaskDto.getTitle())
                .description(createTaskDto.getDescription())
                .status(status)
                .board(board)
                .build();

        Task createdTask = this.tasksRepository.save(task);

        return this.taskMapper.map(createdTask);
    }

    public TasksResponseSlimDto assign(AssignTaskDto assignTaskDto) {
        Task task = this.findTaskByIdElseThrow(assignTaskDto.getTaskId());

        User userToAssign;

        if (!Objects.equals(assignTaskDto.getCurrentUser().getId(), assignTaskDto.getAssignUserId())) {
            userToAssign = this.userService.findUserByIdElseThrow(assignTaskDto.getAssignUserId());
        } else {
            userToAssign = assignTaskDto.getCurrentUser();
        }

        this.boardService.findUserAssociatedBoardElseThrow(task.getBoard().getId(), userToAssign.getId());

        task.setAssigned(userToAssign);

        Task updatedTask = this.tasksRepository.save(task);

        return this.taskMapper.map(updatedTask);
    }

    public void changeTaskStatus(ChangeTaskStatusDto changeTaskStatusDto) {
        Task task = this.findTaskByIdElseThrow(changeTaskStatusDto.getTaskId());

        Status newStatus = this.statusService.findStatusByIdElseThrow(changeTaskStatusDto.getStatusId());

        BoardMembers boardMember = this.boardService.findUserAssociatedBoardElseThrow(
                newStatus.getBoard().getId(),
                changeTaskStatusDto.getUser().getId()
        );

        if (!Objects.equals(newStatus.getBoard().getId(), boardMember.getBoard().getId())) {
            throw new BadRequestException("Status does not exists in this board!");
        }

        task.setStatus(newStatus);

        this.tasksRepository.save(task);
    }

    public List<TasksResponseSlimDto> findTasksByUserId(User currentUser, Integer boardId, Integer userId) {
        this.boardService.findBoardByIdElseThrow(boardId);

        if (Objects.equals(currentUser.getId(), userId)) {
            this.boardService.findUserAssociatedBoardElseThrow(boardId, userId);
        } else  {
            this.boardService.findUserAssociatedBoardElseThrow(boardId,currentUser.getId());

            this.boardService.findUserAssociatedBoardElseThrow(boardId, userId);
        }

        List<Task> tasks = this.tasksRepository.findByAssignedId(userId);

        return this.taskMapper.mapList(tasks);
    }

    public List<TasksResponseSlimDto> findTasksByBoardId(User user, Integer boardId) {
        Board board = this.boardService.findBoardByIdElseThrow(boardId);

        this.boardService.findUserAssociatedBoardElseThrow(board.getId(), user.getId());

        List<Task> tasks = this.tasksRepository.findByBoardId(board.getId());

        return this.taskMapper.mapList(tasks);
    }

//    public List<TasksResponseSlimDto> findTaskById(User user, Integer boardId, Integer taskId) {
//        Board board = this.boardService.findBoardByIdElseThrow(boardId);
//
//        this.boardService.findUserAssociatedBoardElseThrow(board.getId(), user.getId());
//    }

    public void deleteTaskById(User user, Integer boardId, Integer taskId) {
        Board board = this.boardService.findBoardByIdElseThrow(boardId);

        BoardMembers boardMember = this.boardService.findUserAssociatedBoardElseThrow(board.getId(), user.getId());

        this.boardService.isBoardMemberBoardAdminElseThrow(boardMember);

        Task task = this.findTaskByIdElseThrow(taskId);

        this.tasksRepository.delete(task);
    }
}
