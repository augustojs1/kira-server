package com.augustodev.kiraserver.modules.invites;

import com.augustodev.kiraserver.modules.invites.dtos.InviteUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;

    public void invite(InviteUserDto inviteUserDto) {
        System.out.println("inviteUserDto.:" + inviteUserDto);
    }
}
