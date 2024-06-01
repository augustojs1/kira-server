package com.augustodev.kiraserver.modules.tasks;

import com.augustodev.kiraserver.modules.tasks.dtos.request.CreateTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.response.CreateTaskResponseDto;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TasksController {
    private final TasksService tasksService;
    @PostMapping("/board/{boardId}")
    public ResponseEntity<CreateTaskResponseDto> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer boardId,
            @RequestBody CreateTaskDto createTaskDto
            ) {
        User user = (User) userDetails;

        return new ResponseEntity<>(this.tasksService.create(createTaskDto, boardId, user), HttpStatus.CREATED);
    }
}
