package com.augustodev.kiraserver.modules.auth;

import com.augustodev.kiraserver.common.exceptions.BadRequestResponse;
import com.augustodev.kiraserver.modules.auth.dtos.request.SignInDto;
import com.augustodev.kiraserver.modules.auth.dtos.response.SignInResponseDto;
import com.augustodev.kiraserver.modules.auth.dtos.request.SignUpDto;
import com.augustodev.kiraserver.modules.boards.dtos.response.BoardCreatedDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("local/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Collection of REST API endpoints to sign in and sign up.")
public class AuthController {
    private final AuthService authService;

    @Operation(
            description = "Register a new user.",
            summary = "Register a new user.",
            responses = {
                    @ApiResponse(
                            description = "Invalid request body",
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
                            description = "User with the same email already exists",
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
                                            schema = @Schema(implementation = SignInResponseDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<SignInResponseDto> register(
            @Valid
            @RequestBody SignUpDto request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(
            description = "Authenticate an existing user.",
            summary = "Authenticate an existing user.",
            responses = {
                    @ApiResponse(
                            description = "Invalid request body",
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
                            description = "User not found",
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
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = SignInResponseDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> authenticateRequest(
            @Valid
            @RequestBody SignInDto request
    )  {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
