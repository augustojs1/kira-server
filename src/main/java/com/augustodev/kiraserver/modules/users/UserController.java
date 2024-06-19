package com.augustodev.kiraserver.modules.users;

import com.augustodev.kiraserver.modules.users.dtos.UserAuthDto;
import com.augustodev.kiraserver.modules.users.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User", description = "A single REST API endpoint to get the current logged in user.")
public class UserController {

    @Operation(
            description = "Get a list of boards the current user is a member.",
            summary = "Get a list of boards the current user is a member.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserAuthDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @GetMapping("/me")
    public ResponseEntity<UserAuthDto> getMe(@AuthenticationPrincipal User user) {

        UserAuthDto currentUser = UserAuthDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();

        return ResponseEntity.ok(currentUser);
    }
}
