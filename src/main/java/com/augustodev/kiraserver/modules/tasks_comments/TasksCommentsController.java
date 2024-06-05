package com.augustodev.kiraserver.modules.tasks_comments;

import com.augustodev.kiraserver.modules.tasks_comments.dtos.request.CreateTaskCommentDto;
import com.augustodev.kiraserver.modules.tasks_comments.dtos.response.TaskCommentSlimDto;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task-comment")
public class TasksCommentsController {
    private  final   TasksCommentsService tasksCommentsService;

    @PostMapping("/task/{taskId}")
    public ResponseEntity<TaskCommentSlimDto> createTaskComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateTaskCommentDto createTaskCommentDto,
            @PathVariable Integer taskId
    ) {
        User user = (User) userDetails;

        return new ResponseEntity<>(
                this.tasksCommentsService.create(createTaskCommentDto, user, taskId),
                HttpStatus.CREATED
        );
    }
}
