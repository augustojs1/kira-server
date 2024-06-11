package com.augustodev.kiraserver.modules.auth;

import com.augustodev.kiraserver.modules.auth.dtos.request.SignInDto;
import com.augustodev.kiraserver.modules.auth.dtos.response.SignInResponseDto;
import com.augustodev.kiraserver.modules.auth.dtos.request.SignUpDto;
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
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignInResponseDto> register(
            @Valid
            @RequestBody SignUpDto request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> authenticateRequest(
            @Valid
            @RequestBody SignInDto request
    )  {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
