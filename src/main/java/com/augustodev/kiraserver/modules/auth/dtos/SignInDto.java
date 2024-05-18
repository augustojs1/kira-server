package com.augustodev.kiraserver.modules.auth.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    private String email;
    String password;
}
