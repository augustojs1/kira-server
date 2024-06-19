package com.augustodev.kiraserver.modules.tasks_comments;

import com.augustodev.kiraserver.common.exceptions.BadRequestResponse;
import com.augustodev.kiraserver.common.exceptions.UnauthorizedResponse;
import com.augustodev.kiraserver.modules.tasks_comments.dtos.request.CreateTaskCommentDto;
import com.augustodev.kiraserver.modules.tasks_comments.dtos.response.TaskCommentSlimDto;
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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task-comment")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Task Comments", description = "Collection of REST API endpoints to comment on a task.")
public class TasksCommentsController {
    private final TasksCommentsService tasksCommentsService;

    @Operation(
            description = "Create a new comment on a task.",
            summary = "Create a new comment on a task.",
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
                            description = "Invalid request body.",
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Task not found.",
                            responseCode = "404",
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
                                            schema = @Schema(implementation = TaskCommentSlimDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
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

    @Operation(
            description = "Update an existing comment.",
            summary = "Update an existing comment.",
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
                            description = "User is not comment owner",
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
                            description = "Invalid request body.",
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Task not found.",
                            responseCode = "404",
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
                                            schema = @Schema(implementation = TaskCommentSlimDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
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

    @Operation(
            description = "Delete an existing comment.",
            summary = "Delete an existing comment.",
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
                            description = "User is not comment owner",
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
                            description = "Invalid request body.",
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BadRequestResponse.class
                                            )
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Comment not found.",
                            responseCode = "404",
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
                            responseCode = "204"

                    ),
            }
    )
    @DeleteMapping("/{taskId}")
    public ResponseEntity delete(
            @AuthenticationPrincipal User user,
            @PathVariable Integer taskId
    ) {
        this.tasksCommentsService.delete(user, taskId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
