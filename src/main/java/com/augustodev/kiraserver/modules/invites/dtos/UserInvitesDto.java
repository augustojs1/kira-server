package com.augustodev.kiraserver.modules.invites.dtos;

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
    private UserSlimDto inviter;
    private BoardSlimDto board;
    private Instant updatedAt;
    private Instant createdAt;
}
