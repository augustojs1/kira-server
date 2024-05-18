package com.augustodev.kiraserver.modules.auth;

import com.augustodev.kiraserver.modules.auth.dtos.SignInDto;
import com.augustodev.kiraserver.modules.auth.dtos.SignInResponseDto;
import com.augustodev.kiraserver.modules.auth.dtos.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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
            @RequestBody SignUpDto request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> authenticateRequest(
            @RequestBody SignInDto request
    ) throws BadRequestException {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
