package com.augustodev.kiraserver.modules.invites.dtos.request;

import com.augustodev.kiraserver.modules.users.enums.Role;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class InviteUserDto {
    @NotNull(message = "boardId is required.")
    @Digits(fraction = 0, integer = 10, message = "boardId should be a number.")
    @Positive(message = "boardId should be a positive number.")
    private Integer boardId;

    @NotNull(message = "invitedId is required.")
    @Digits(fraction = 0, integer = 10, message = "invitedId should be a number.")
    @Positive(message = "invitedId should be a positive number.")
    private Integer invitedId;

    @NotNull(message = "invitedId is required.")
    private Role role;
}
