package com.augustodev.kiraserver.modules.tasks;

import com.augustodev.kiraserver.modules.tasks.dtos.request.AssignTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.request.ChangeTaskStatusDto;
import com.augustodev.kiraserver.modules.tasks.dtos.request.CreateTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.response.TaskWithCommentsDto;
import com.augustodev.kiraserver.modules.tasks.dtos.response.TasksResponseSlimDto;
import com.augustodev.kiraserver.modules.users.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TasksController {
    private final TasksService tasksService;
    @PostMapping("/board/{boardId}")
    public ResponseEntity<TasksResponseSlimDto> create(
            @Valid
            @RequestBody CreateTaskDto createTaskDto,
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId
            ) {
        return new ResponseEntity<>(this.tasksService.create(createTaskDto, boardId, user), HttpStatus.CREATED);
    }

    @PatchMapping("/board/{boardId}/task/{taskId}/assign/{assignId}")
    public ResponseEntity<TasksResponseSlimDto> assign(
            @AuthenticationPrincipal User user,
            @PathVariable Integer taskId,
            @PathVariable Integer assignId
    ) {
        AssignTaskDto assignTaskDto = AssignTaskDto.builder()
                .assignUserId(assignId)
                .currentUser(user)
                .taskId(taskId)
                .build();

        return new ResponseEntity<>(this.tasksService.assign(assignTaskDto), HttpStatus.OK);
    }

    @PatchMapping("/board/{boardId}/task/{taskId}/status/{statusId}")
    public void changeTaskStatus(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId,
            @PathVariable Integer taskId,
            @PathVariable Integer statusId
    ) {
        ChangeTaskStatusDto changeTaskStatusDto = ChangeTaskStatusDto.builder()
                .user(user)
                .taskId(taskId)
                .statusId(statusId)
                .boardId(boardId)
                .build();

        this.tasksService.changeTaskStatus(changeTaskStatusDto);
    }

    @GetMapping("/board/{boardId}/user/{userId}/task")
    public ResponseEntity<List<TasksResponseSlimDto>> getUserTasks(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId,
            @PathVariable Integer userId
    ) {
        return new ResponseEntity<>(this.tasksService.findTasksByUserId(user, boardId, userId), HttpStatus.OK);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<TasksResponseSlimDto>> getBoardTasks(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId
    ) {
        return new ResponseEntity<>(this.tasksService.findTasksByBoardId(user, boardId), HttpStatus.OK);
    }

    @GetMapping("/board/{boardId}/task/{taskId}")
    public ResponseEntity<TaskWithCommentsDto> getTaskById(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId,
            @PathVariable Integer taskId
    ) {
        return new ResponseEntity<>(this.tasksService.findTaskById(user, boardId, taskId), HttpStatus.OK);
    }

    @DeleteMapping("/board/{boardId}/task/{taskId}")
        public ResponseEntity deleteTask(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId,
            @PathVariable Integer taskId
    ) {
        this.tasksService.deleteTaskById(user, boardId, taskId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
