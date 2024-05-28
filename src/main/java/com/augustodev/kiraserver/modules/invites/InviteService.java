package com.augustodev.kiraserver.modules.invites;

import com.augustodev.kiraserver.common.exceptions.BadRequestException;
import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundException;
import com.augustodev.kiraserver.modules.boards.BoardService;
import com.augustodev.kiraserver.modules.boards.dtos.AssociateUserToBoardDto;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.invites.dtos.InviteUserDto;
import com.augustodev.kiraserver.modules.invites.dtos.UserInvitesDto;
import com.augustodev.kiraserver.modules.invites.entities.Invite;
import com.augustodev.kiraserver.modules.invites.mapper.InvitesMapper;
import com.augustodev.kiraserver.modules.users.UserService;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private final BoardService boardService;
    private final UserService userService;
    private final InvitesMapper invitesMapper;

    public void findIfUserNotInvitedElseThrow(Integer invitedId) {
        Optional<Invite> invite = this.inviteRepository.findByInvitedIdAndActiveIsTrue(invitedId);

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
                         .active(true)
                         .role(inviteUserDto.getRole()).build();

        return this.inviteRepository.save(invite);
    }

    public List<UserInvitesDto> findInvitesById(Integer userId) {
        Optional<Invite> invites = this.inviteRepository.findByInvitedIdAndActiveIsTrue(userId);

        return this.invitesMapper.invitesToSlimDto(invites);
    }

    public List<UserInvitesDto> findSentInvites(Integer userId) {
        Optional<Invite> invites = this.inviteRepository.findByInviterIdAndActiveIsTrue(userId);

        return this.invitesMapper.invitesToSlimDto(invites);
    }

    public void accept(Integer userId, Integer inviteId) {
        Optional<Invite> inviteOpt = this.inviteRepository.findById(inviteId);

        if (inviteOpt.isEmpty()) {
            throw new ResourceNotFoundException("Invite with this id not found!");
        }

        Invite invite = inviteOpt.get();

        invite.setActive(false);
        invite.setAccepted(true);

        this.boardService.associateUserToBoard(
               invite.getInvited(),
               invite.getBoard(),
               invite.getRole()
        );

        this.inviteRepository.save(invite);
    }
}
