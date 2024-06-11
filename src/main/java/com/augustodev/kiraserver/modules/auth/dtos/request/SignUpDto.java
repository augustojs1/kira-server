package com.augustodev.kiraserver.modules.auth.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    @NotNull(message = "firstname should not be null.")
    @NotBlank(message = "firstname is required.")
    private String firstname;

    @NotNull(message = "lastname should not be null.")
    @NotBlank(message = "lastname is required.")
    private String lastname;

    @NotNull(message = "email should not be null.")
    @NotBlank(message = "email is required.")
    @Email(message = "email should be a valid email")
    private String email;

    @NotNull(message = "password should not be null.")
    @NotBlank(message = "password is required.")
    private String password;
}
