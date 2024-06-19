package com.augustodev.kiraserver.modules.status;

import com.augustodev.kiraserver.common.exceptions.BadRequestResponse;
import com.augustodev.kiraserver.common.exceptions.UnauthorizedResponse;
import com.augustodev.kiraserver.modules.invites.dtos.response.UserInvitesDto;
import com.augustodev.kiraserver.modules.status.dtos.request.ChangeStatusPositionDto;
import com.augustodev.kiraserver.modules.status.dtos.request.CreateStatusDto;
import com.augustodev.kiraserver.modules.status.dtos.response.CreateStatusResponseDto;
import com.augustodev.kiraserver.modules.status.dtos.response.StatusResponseDto;
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

import java.util.List;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Status", description = "Collection of REST API endpoints to create new status or change tasks status.")
public class StatusController {
    private final StatusService statusService;

    @Operation(
            description = "Create a new status in a board.",
            summary = "Create a new status in a board.",
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
                            responseCode = "201",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CreateStatusResponseDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<CreateStatusResponseDto> create(
                @Valid
                @RequestBody CreateStatusDto createStatusDto,
                @AuthenticationPrincipal User user
            ) {
        return new ResponseEntity<>(this.statusService.create(createStatusDto, user.getId()), HttpStatus.CREATED);
    }

    @Operation(
            description = "Get all status from a board.",
            summary = "Get all status from a board.",
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
                                            schema = @Schema(implementation = StatusResponseDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<StatusResponseDto>> getStatus(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId
    ) {
        return new ResponseEntity<>(this.statusService.findAllByBoardId(boardId, user.getId()), HttpStatus.OK);
    }

    @Operation(
            description = "Change status order.",
            summary = "Change status order.",
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
                            responseCode = "200"
                    ),
            }
    )
    @PatchMapping("/position")
    public ResponseEntity changeStatusPosition(
            @Valid
            @RequestBody ChangeStatusPositionDto changeStatusPositionDto,
            @AuthenticationPrincipal User user
    ) {
        this.statusService.changePosition(changeStatusPositionDto, user.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            description = "Delete a status from a board.",
            summary = "Delete a status from a board.",
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
                            responseCode = "204"
                    ),
            }
    )
    @DeleteMapping("/{statusId}")
    public ResponseEntity deleteStatus(
            @AuthenticationPrincipal User user,
            @PathVariable Integer statusId
    ) {
        this.statusService.deleteStatusById(user, statusId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
