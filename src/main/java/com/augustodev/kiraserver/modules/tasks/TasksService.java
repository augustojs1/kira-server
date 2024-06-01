package com.augustodev.kiraserver.modules.tasks;

import com.augustodev.kiraserver.modules.boards.BoardService;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import com.augustodev.kiraserver.modules.status.StatusService;
import com.augustodev.kiraserver.modules.status.entities.Status;
import com.augustodev.kiraserver.modules.tasks.dtos.request.CreateTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.response.CreateTaskResponseDto;
import com.augustodev.kiraserver.modules.tasks.entities.Task;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TasksService {
    private final StatusService statusService;
    private final BoardService boardService;
    private final TasksRepository tasksRepository;

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
}
