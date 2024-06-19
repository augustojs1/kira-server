package com.augustodev.kiraserver.modules.tasks;

import com.augustodev.kiraserver.common.exceptions.BadRequestResponse;
import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundResponse;
import com.augustodev.kiraserver.common.exceptions.UnauthorizedResponse;
import com.augustodev.kiraserver.modules.tasks.dtos.request.AssignTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.request.ChangeTaskStatusDto;
import com.augustodev.kiraserver.modules.tasks.dtos.request.CreateTaskDto;
import com.augustodev.kiraserver.modules.tasks.dtos.response.TaskWithCommentsDto;
import com.augustodev.kiraserver.modules.tasks.dtos.response.TasksResponseSlimDto;
import com.augustodev.kiraserver.modules.users.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Tasks", description = "Collection of REST API endpoints to create tasks for a board.")
public class TasksController {
    private final TasksService tasksService;

    @Operation(
            description = "Create a new task.",
            summary = "Create a new task.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UnauthorizedResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "401",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "401",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TasksResponseSlimDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @PostMapping("/board/{boardId}")
    public ResponseEntity<TasksResponseSlimDto> create(
            @Valid
            @RequestBody CreateTaskDto createTaskDto,
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId
            ) {
        return new ResponseEntity<>(this.tasksService.create(createTaskDto, boardId, user), HttpStatus.CREATED);
    }

    @Operation(
            description = "Assign a task to a board member.",
            summary = "Assign a task to a board member.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UnauthorizedResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "401",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "401",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TasksResponseSlimDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
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

    @Operation(
            description = "Assign a task to a board member.",
            summary = "Assign a task to a board member.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UnauthorizedResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "401",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "401",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TasksResponseSlimDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
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

    @Operation(
            description = "Get all tasks assigned to a user.",
            summary = "Get all tasks assigned to a user.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UnauthorizedResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "401",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TasksResponseSlimDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @GetMapping("/board/{boardId}/user/{userId}/task")
    public ResponseEntity<List<TasksResponseSlimDto>> getUserTasks(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId,
            @PathVariable Integer userId
    ) {
        return new ResponseEntity<>(this.tasksService.findTasksByUserId(user, boardId, userId), HttpStatus.OK);
    }

    @Operation(
            description = "Get all tasks in a board.",
            summary = "Get all tasks in a board.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UnauthorizedResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "401",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TasksResponseSlimDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<TasksResponseSlimDto>> getBoardTasks(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId
    ) {
        return new ResponseEntity<>(this.tasksService.findTasksByBoardId(user, boardId), HttpStatus.OK);
    }

    @Operation(
            description = "Get a task by id.",
            summary = "Get a task by id.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UnauthorizedResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "401",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Task not found",
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResourceNotFoundResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TasksResponseSlimDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @GetMapping("/board/{boardId}/task/{taskId}")
    public ResponseEntity<TaskWithCommentsDto> getTaskById(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId,
            @PathVariable Integer taskId
    ) {
        return new ResponseEntity<>(this.tasksService.findTaskById(user, boardId, taskId), HttpStatus.OK);
    }

    @Operation(
            description = "Delete a task by id.",
            summary = "Delete a task by id.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UnauthorizedResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "401",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Task not found",
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResourceNotFoundResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    ),
            }
    )
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
