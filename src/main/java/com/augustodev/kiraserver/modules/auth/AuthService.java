package com.augustodev.kiraserver.modules.auth;

import com.augustodev.kiraserver.modules.auth.dtos.request.SignInDto;
import com.augustodev.kiraserver.modules.auth.dtos.response.SignInResponseDto;
import com.augustodev.kiraserver.modules.auth.dtos.request.SignUpDto;
import com.augustodev.kiraserver.modules.auth.strategies.JwtService;
import com.augustodev.kiraserver.modules.users.UserRepository;
import com.augustodev.kiraserver.modules.users.entities.User;
import com.augustodev.kiraserver.modules.users.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.augustodev.kiraserver.common.exceptions.BadRequestException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public SignInResponseDto register(SignUpDto request) {
        boolean emailAlreadyExists = userRepository.findByEmail(request.getEmail()).isPresent();

        if (emailAlreadyExists) {
            throw new BadRequestException("User with this email already exists!");
        }

        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return  SignInResponseDto
                .builder()
                .token(jwtToken)
                .build();
    }

    public SignInResponseDto authenticate(SignInDto request) throws BadRequestException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return  SignInResponseDto
                .builder()
                .token(jwtToken)
                .build();
    }
}
