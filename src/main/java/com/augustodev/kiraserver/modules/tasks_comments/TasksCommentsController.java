package com.augustodev.kiraserver.modules.tasks_comments;

import com.augustodev.kiraserver.modules.tasks_comments.dtos.request.CreateTaskCommentDto;
import com.augustodev.kiraserver.modules.tasks_comments.dtos.response.TaskCommentSlimDto;
import com.augustodev.kiraserver.modules.users.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task-comment")
public class TasksCommentsController {
    private final TasksCommentsService tasksCommentsService;

    @PostMapping("/task/{taskId}")
    public ResponseEntity<TaskCommentSlimDto> createTaskComment(
            @Valid
            @RequestBody CreateTaskCommentDto createTaskCommentDto,
            @AuthenticationPrincipal User user,
            @PathVariable Integer taskId
    ) {
        return new ResponseEntity<>(
                this.tasksCommentsService.create(createTaskCommentDto, user, taskId),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskCommentSlimDto> update(
            @Valid
            @RequestBody CreateTaskCommentDto createTaskCommentDto,
            @AuthenticationPrincipal User user,
            @PathVariable Integer taskId
    ) {
        return new ResponseEntity<>(
                this.tasksCommentsService.update(createTaskCommentDto, user, taskId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity delete(
            @AuthenticationPrincipal User user,
            @PathVariable Integer taskId
    ) {
        this.tasksCommentsService.delete(user, taskId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
