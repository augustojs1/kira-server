package com.augustodev.kiraserver.modules.invites.dtos;

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
    private Integer userId;
    private Integer invitedId;
}
