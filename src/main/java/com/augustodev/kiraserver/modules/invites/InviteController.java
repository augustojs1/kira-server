package com.augustodev.kiraserver.modules.invites;

import com.augustodev.kiraserver.modules.invites.dtos.InviteUserDto;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invites")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;

    @PostMapping("/board/{boardId}/user/{invitedId}")
    public void invite(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer boardId,
            @PathVariable Integer invitedId
            ) {
            User user = (User) userDetails;

        InviteUserDto inviteUserDto = InviteUserDto.builder()
                                    .boardId(boardId)
                                    .userId(user.getId())
                                    .invitedId(invitedId)
                                    .build();

            this.inviteService.invite(inviteUserDto);
    }
}
