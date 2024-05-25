package com.augustodev.kiraserver.modules.invites.dtos;

import com.augustodev.kiraserver.modules.users.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class InviteUserDto {
    private Integer boardId;
    private Integer invitedId;
    private Role role;
}
