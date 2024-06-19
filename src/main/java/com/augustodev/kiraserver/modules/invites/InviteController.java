package com.augustodev.kiraserver.modules.invites;

import com.augustodev.kiraserver.common.exceptions.BadRequestResponse;
import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundException;
import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundResponse;
import com.augustodev.kiraserver.common.exceptions.UnauthorizedResponse;
import com.augustodev.kiraserver.modules.invites.dtos.request.InviteUserDto;
import com.augustodev.kiraserver.modules.invites.dtos.response.UserInvitesDto;
import com.augustodev.kiraserver.modules.invites.entities.Invite;
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

import java.util.List;

@RestController
@RequestMapping("/invites")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Invites", description = "Collection of REST API endpoints to manage board sent and received invites.")
public class InviteController {
    private final InviteService inviteService;

    @Operation(
            description = "Invite a user to a board.",
            summary = "Invite a user to a board.",
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
                            description = "Board not found.",
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
                            description = "User not found.",
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
                                            schema = @Schema(implementation = Invite.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<Invite> invite(
                @Valid
                @RequestBody InviteUserDto inviteUserDto,
                @AuthenticationPrincipal User user
            ) {
            return new ResponseEntity<>(this.inviteService.invite(user, inviteUserDto), HttpStatus.CREATED);
    }

    @Operation(
            description = "Get a list of user invites.",
            summary = "Get a list of user invites.",
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
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserInvitesDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<List<UserInvitesDto>> getInvites(
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(this.inviteService.findInvitesById(user.getId()), HttpStatus.OK);
    }

    @Operation(
            description = "Get a list of user sent invites.",
            summary = "Get a list of user sent invites.",
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
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserInvitesDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @GetMapping("/sent")
    public ResponseEntity<List<UserInvitesDto>> getSentInvites(
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(this.inviteService.findSentInvites(user.getId()), HttpStatus.OK);
    }

    @Operation(
            description = "Accept a pending invite.",
            summary = "Accept a pending invite.",
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
                            description = "Resource not found.",
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
                            responseCode = "200"
                    ),
            }
    )
    @PostMapping("/accept/{inviteId}")
    public ResponseEntity acceptInvite(
            @AuthenticationPrincipal User user,
            @PathVariable Integer inviteId
    ) {
        this.inviteService.accept(inviteId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            description = "Deny a pending invite.",
            summary = "Deny a pending invite.",
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
                            description = "Resource not found.",
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
    @PatchMapping("/deny/{inviteId}")
    public ResponseEntity denyInvite(
            @AuthenticationPrincipal User user,
            @PathVariable Integer inviteId
    ) {
        this.inviteService.deny(inviteId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
