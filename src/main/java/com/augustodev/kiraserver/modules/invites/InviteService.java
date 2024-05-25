package com.augustodev.kiraserver.modules.invites;

import com.augustodev.kiraserver.common.exceptions.BadRequestException;
import com.augustodev.kiraserver.modules.boards.BoardService;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.invites.dtos.InviteUserDto;
import com.augustodev.kiraserver.modules.invites.entities.Invite;
import com.augustodev.kiraserver.modules.users.UserService;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private final BoardService boardService;
    private final UserService userService;

    public void findIfUserNotInvitedElseThrow(Integer invitedId) {
        Optional<Invite> invite = this.inviteRepository.findByInvitedId(invitedId);

        if (invite.isPresent()) {
            throw new BadRequestException("User with this Id has already been invited!");
        }
    }

    public Invite invite(User user, InviteUserDto inviteUserDto) {
        Board board = this.boardService.findBoardByIdElseThrow(inviteUserDto.getBoardId());

        this.boardService.checkIfUserIsBoardAdminElseThrow(
                board.getId(),
                user.getId()
        );

        User invitedUser = this.userService.findUserByIdElseThrow(inviteUserDto.getInvitedId());

        this.findIfUserNotInvitedElseThrow(inviteUserDto.getInvitedId());

         Invite invite = Invite.builder()
                        .board(board)
                         .invited(invitedUser)
                         .inviter(user)
                         .accepted(false)
                         .role(inviteUserDto.getRole()).build();

        return this.inviteRepository.save(invite);
    }
}
