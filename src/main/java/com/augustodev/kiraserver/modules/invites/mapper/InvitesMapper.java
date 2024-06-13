package com.augustodev.kiraserver.modules.invites.mapper;

import com.augustodev.kiraserver.modules.invites.dtos.response.BoardSlimDto;
import com.augustodev.kiraserver.modules.invites.dtos.response.UserInvitesDto;
import com.augustodev.kiraserver.modules.invites.dtos.response.UserSlimDto;
import com.augustodev.kiraserver.modules.invites.entities.Invite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class InvitesMapper {
    public List<UserInvitesDto> invitesToSlimDto(Optional<Invite> invites) {

        if (invites.isEmpty()) {
            return  null;
        }

        return invites.stream()
                .map(invite -> new UserInvitesDto(
                        invite.getId(),
                        new UserSlimDto(invite.getInviter().getId(), invite.getInviter().getFirstName(), invite.getInviter().getLastName(), invite.getInviter().getEmail()),
                        new BoardSlimDto(invite.getBoard().getId(), invite.getBoard().getTitle(), invite.getBoard().getDescription()),
                        invite.getUpdatedAt(),
                        invite.getCreatedAt()
                ))
                .toList();
    }
}
