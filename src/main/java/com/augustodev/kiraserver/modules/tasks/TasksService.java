package com.augustodev.kiraserver.modules.tasks;

import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundException;
import com.augustodev.kiraserver.modules.boards.BoardService;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import com.augustodev.kiraserver.modules.status.StatusService;
import com.augustodev.kiraserver.modules.status.entities.Status;
import com.augustodev.kiraserver.modules.tasks.dtos.request.AssignTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.request.CreateTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.response.CreateTaskResponseDto;
import com.augustodev.kiraserver.modules.tasks.entities.Task;
import com.augustodev.kiraserver.modules.users.UserService;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;
    private final StatusService statusService;
    private final BoardService boardService;
    private final UserService userService;

    public Task findTaskByIdElseThrow(Integer taskId) {
        Optional<Task> taskOpt = this.tasksRepository.findById(taskId);

        if (taskOpt.isEmpty()) {
            throw new ResourceNotFoundException("Task with this id not found!");
        }

        return taskOpt.get();
    }

    public CreateTaskResponseDto create(CreateTaskDto createTaskDto, Integer boardId, User user) {
        Board board = this.boardService.findBoardByIdElseThrow(boardId);

        BoardMembers boardMember = this.boardService.findUserAssociatedBoardElseThrow(board.getId(), user.getId());

        this.boardService.isBoardMemberBoardAdminElseThrow(boardMember);

        Status status = this.statusService.findStatusByIdElseThrow(createTaskDto.getStatus_id());

        Task task = Task.builder()
                .title(createTaskDto.getTitle())
                .description(createTaskDto.getDescription())
                .status(status)
                .board(board)
                .build();

        Task createdTask = this.tasksRepository.save(task);

        return CreateTaskResponseDto.builder()
                .title(createdTask.getTitle())
                .description(createdTask.getDescription())
                .build();
    }

    public CreateTaskResponseDto assign(AssignTaskDto assignTaskDto) {
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

        return CreateTaskResponseDto.builder()
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .build();
    }
}
