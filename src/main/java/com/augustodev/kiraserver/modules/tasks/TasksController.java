package com.augustodev.kiraserver.modules.tasks;

import com.augustodev.kiraserver.modules.tasks.dtos.request.AssignTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.request.ChangeTaskStatusDto;
import com.augustodev.kiraserver.modules.tasks.dtos.request.CreateTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.response.TasksResponseSlimDto;
import com.augustodev.kiraserver.modules.users.entities.User;
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
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer boardId,
            @RequestBody CreateTaskDto createTaskDto
            ) {
        User user = (User) userDetails;

        return new ResponseEntity<>(this.tasksService.create(createTaskDto, boardId, user), HttpStatus.CREATED);
    }

    @PatchMapping("/board/{boardId}/task/{taskId}/assign/{assignId}")
    public ResponseEntity<TasksResponseSlimDto> assign(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer taskId,
            @PathVariable Integer assignId
    ) {
        User user = (User) userDetails;

        AssignTaskDto assignTaskDto = AssignTaskDto.builder()
                .assignUserId(assignId)
                .currentUser(user)
                .taskId(taskId)
                .build();

        return new ResponseEntity<>(this.tasksService.assign(assignTaskDto), HttpStatus.OK);
    }

    @PatchMapping("/board/{boardId}/task/{taskId}/status/{statusId}")
    public void changeTaskStatus(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer boardId,
            @PathVariable Integer taskId,
            @PathVariable Integer statusId
    ) {
        User user = (User) userDetails;

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
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer boardId,
            @PathVariable Integer userId
    ) {
        User user = (User) userDetails;

        return new ResponseEntity<>(this.tasksService.findTasksByUserId(user, boardId, userId), HttpStatus.OK);
    }
}
