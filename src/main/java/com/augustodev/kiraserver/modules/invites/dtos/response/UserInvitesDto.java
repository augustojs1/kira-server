package com.augustodev.kiraserver.modules.invites.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserInvitesDto {
    private Integer id;
    private UserSlimDto inviter;
    private BoardSlimDto board;
    private Instant updatedAt;
    private Instant createdAt;
}
