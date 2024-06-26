package com.augustodev.kiraserver.modules.boards;

import com.augustodev.kiraserver.common.exceptions.BadRequestException;
import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundException;
import com.augustodev.kiraserver.common.exceptions.UnauthorizedException;
import com.augustodev.kiraserver.modules.boards.dtos.request.CreateBoardDto;
import com.augustodev.kiraserver.modules.boards.dtos.request.RemoveUserFromBoardDto;
import com.augustodev.kiraserver.modules.boards.dtos.request.UpdateBoardDto;
import com.augustodev.kiraserver.modules.boards.dtos.response.BoardCreatedDto;
import com.augustodev.kiraserver.modules.boards.dtos.response.BoardDTO;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import com.augustodev.kiraserver.modules.boards.mapper.BoardMapper;
import com.augustodev.kiraserver.modules.users.entities.User;
import com.augustodev.kiraserver.modules.users.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardMemberRepository boardMemberRepository;
    private final BoardMapper boardMapper;

    public Board findBoardByIdElseThrow(Integer boardId) {

        Optional<Board> board = this.boardRepository.findById(boardId);

        if (board.isEmpty()) {
            log.error("Board with this Id not found!");
            throw new ResourceNotFoundException("Board with this Id not found!");
        }

        return board.get();
    }

    public void isBoardMemberBoardAdminElseThrow(BoardMembers boardMember) {
        if (boardMember.getRole() != Role.ADMIN) {
            log.error("User is unauthorized to perform this action!");
            throw new UnauthorizedException("User is unauthorized to perform this action!");
        }
    }

    public void checkIfUserIsBoardAdminElseThrow(Integer boardId, Integer userId) {
        BoardMembers boardMember = this.findUserAssociatedBoardElseThrow(boardId, userId);

        if (boardMember.getRole() != Role.ADMIN) {
            log.error("User is unauthorized to perform this action!");
            throw new UnauthorizedException("User is unauthorized to perform this action!");
        }
    }

    public BoardMembers findUserAssociatedBoardElseThrow(Integer boardId, Integer userId) {
        Optional<BoardMembers> boardMembers = this.boardMemberRepository.findByBoardIdAndUserId(boardId, userId);

        if (boardMembers.isEmpty()) {
            log.error("User is not a member of this board!");
            throw new UnauthorizedException("User is not a member of this board!");
        }

        return boardMembers.get();
    }

    public List<BoardDTO> findAssociateBoards(Integer userId) {
        Optional<BoardMembers> boardOpt = this.boardMemberRepository.findByUserId(userId);

        return this.boardMapper.boardsToSlimDto(boardOpt);
    }

    public void checkIfUserIsAlreadyABoardMember(Integer boardId, Integer invitedId) {
        Optional<BoardMembers> boardMembersOpt = this.boardMemberRepository.findByBoardIdAndUserId(boardId, invitedId);

        if (boardMembersOpt.isPresent()) {
            log.error("User is already a board member!");
            throw new BadRequestException("User is already a board member!");
        }
    }

    public BoardCreatedDto createBoard(CreateBoardDto createBoardDto, User user) {
        Optional<Board> existentBoard = this.boardRepository.findByTitle(createBoardDto.getTitle());

        if (existentBoard.isPresent()) {
            log.error("Board with this title already exists!");
            throw new BadRequestException("Board with this title already exists!");
        }

        Board board = Board.builder()
                .title(createBoardDto.getTitle())
                .description(createBoardDto.getDescription())
                .user(user)
                .build();

        Board boardCreated = this.boardRepository.save(board);

        BoardMembers boardMember = BoardMembers.builder()
                .board(board)
                .user(user)
                .role(Role.ADMIN)
                .build();

        this.boardMemberRepository.save(boardMember);

        BoardCreatedDto response = BoardCreatedDto.builder()
                .id(boardCreated.getId())
                .userId(boardCreated.getUser().getId())
                .title(boardCreated.getTitle())
                .description(boardCreated.getDescription())
                .build();

        log.info("Successfully created board!");

        return response;
    }

    public UpdateBoardDto update(Integer boardId, UpdateBoardDto updateBoardDto, User user) {
        Board boardToUpdateOpt = this.findBoardByIdElseThrow(boardId);

        if (user.getRole() != Role.ADMIN) {
            log.error("User does not have permission to update this board!");
            throw new UnauthorizedException("User does not have permission to update this board!");
        }

        boardToUpdateOpt.setTitle(updateBoardDto.getTitle());
        boardToUpdateOpt.setDescription(updateBoardDto.getDescription());

        Board boardUpdated = this.boardRepository.save(boardToUpdateOpt);

        log.info("Successfully updated board!");

        return new UpdateBoardDto(boardUpdated.getTitle(), boardUpdated.getDescription());
    }

    public void delete(Integer boardId, User user) {
        Board boardToDeleteOpt = this.findBoardByIdElseThrow(boardId);

        if (user.getRole() != Role.ADMIN) {
            log.error("User does not have permission to update this board!");
            throw new UnauthorizedException("User does not have permission to update this board!");
        }

        this.boardRepository.delete(boardToDeleteOpt);
        log.info("Successfully deletee board!");
    }

    public List<User> findUsers(Integer boardId, User user) {
        this.findBoardByIdElseThrow(boardId);

        this.findUserAssociatedBoardElseThrow(boardId, user.getId());

        List<BoardMembers> boardMembersList = this.boardMemberRepository.findByBoardId(boardId);

        log.info("Successfully found board users!");

        return boardMembersList.stream().map(BoardMembers::getUser).collect(Collectors.toList());
    }

    public void associateUserToBoard(User user, Board board, Role role) {
        BoardMembers boardMember = BoardMembers.builder()
                .user(user)
                .board(board)
                .role(role)
                .build();

        this.boardMemberRepository.save(boardMember);

        log.info("Successfully associated user to board!");
    }

    public void removeUser(RemoveUserFromBoardDto removeUserFromBoardDto) {
        if (removeUserFromBoardDto.getUserId() == removeUserFromBoardDto.getMemberId()) {
            log.error("User can not remove itself from a board!");
            throw new UnauthorizedException("User can not remove itself from a board!");
        }

        Optional<Board> boardOptional = this.boardRepository.findById(removeUserFromBoardDto.getBoardId());

        if (boardOptional.isEmpty()) {
            log.error("Board with this id not found!");
            throw new ResourceNotFoundException("Board with this id not found!");
        }

        Optional<BoardMembers> userOpt = this.boardMemberRepository.findByBoardIdAndUserId(
                removeUserFromBoardDto.getBoardId(),
                removeUserFromBoardDto.getUserId()
        );

        if (userOpt.isEmpty()) {
            log.error("Current user is not a member of this board!");
            throw new UnauthorizedException("Current user is not a member of this board!");
        }

        BoardMembers currentUser = userOpt.get();

        BoardMembers memberUser = this.findUserAssociatedBoardElseThrow(
                removeUserFromBoardDto.getBoardId(),
                removeUserFromBoardDto.getMemberId()
                );

        if (currentUser.getRole() != Role.ADMIN) {
            log.error("Only board admins can operate this action!");
            throw new UnauthorizedException("Only board admins can operate this action!");
        }

        if (memberUser.getRole() == Role.ADMIN && !Objects.equals(currentUser.getId(), boardOptional.get().getId())) {
            log.error("Only board owners can remove other board admin!");
            throw new UnauthorizedException("Only board owners can remove other board admin!");
        }

        this.boardMemberRepository.delete(memberUser);

        log.info("Successfully deleted board member!");

    }

    public BoardMembers userToAdmin(RemoveUserFromBoardDto removeUserFromBoardDto) {
        if (removeUserFromBoardDto.getUserId() == removeUserFromBoardDto.getMemberId()) {
            throw new UnauthorizedException("User can not remove itself from a board!");
        }

        Optional<Board> boardOptional = this.boardRepository.findById(removeUserFromBoardDto.getBoardId());

        if (boardOptional.isEmpty()) {
            throw new ResourceNotFoundException("Board with this id not found!");
        }

        Optional<BoardMembers> userOpt = this.boardMemberRepository.findByBoardIdAndUserId(
                removeUserFromBoardDto.getBoardId(),
                removeUserFromBoardDto.getUserId()
        );

        if (userOpt.isEmpty()) {
            throw new UnauthorizedException("Current user is not a member of this board!");
        }

        BoardMembers currentUser = userOpt.get();

        BoardMembers memberUser = this.findUserAssociatedBoardElseThrow(
                removeUserFromBoardDto.getBoardId(),
                removeUserFromBoardDto.getMemberId()
        );

        if (currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("Only board admins can operate this action!");
        }

        memberUser.setRole(Role.ADMIN);

        return this.boardMemberRepository.save(memberUser);
    }
}
