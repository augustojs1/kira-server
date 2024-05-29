package com.augustodev.kiraserver.modules.boards;

import com.augustodev.kiraserver.common.exceptions.BadRequestException;
import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundException;
import com.augustodev.kiraserver.common.exceptions.UnauthorizedException;
import com.augustodev.kiraserver.modules.boards.dtos.*;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import com.augustodev.kiraserver.modules.boards.mapper.BoardMapper;
import com.augustodev.kiraserver.modules.users.entities.User;
import com.augustodev.kiraserver.modules.users.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardMemberRepository boardMemberRepository;
    private final BoardMapper boardMapper;

    public Board findBoardByIdElseThrow(Integer boardId) {
        Optional<Board> board = this.boardRepository.findById(boardId);

        if (board.isEmpty()) {
            throw new ResourceNotFoundException("Board with this Id not found!");
        }

        return board.get();
    }

    public void checkIfUserIsBoardAdminElseThrow(Integer boardId, Integer userId) {
        BoardMembers boardMember = this.findUserAssociatedBoardElseThrow(boardId, userId);

        if (boardMember.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("User is unauthorized to perform this action!");
        }
    }

    public BoardMembers findUserAssociatedBoardElseThrow(Integer boardId, Integer userId) {
        Optional<BoardMembers> boardMembers = this.boardMemberRepository.findByBoardIdAndUserId(boardId, userId);

        if (boardMembers.isEmpty()) {
            throw new UnauthorizedException("User is not a member of this board!");
        }

        return boardMembers.get();
    }

    public List<BoardDTO> findAssociateBoards(Integer userId) {
        Optional<BoardMembers> boardOpt = this.boardMemberRepository.findByUserId(userId);

        return this.boardMapper.boardsToSlimDto(boardOpt);
    }

    public BoardCreatedDto createBoard(CreateBoardDto createBoardDto, User user) {
        Optional<Board> existentBoard = this.boardRepository.findByTitle(createBoardDto.getTitle());

        if (existentBoard.isPresent()) {
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

        return response;
    }

    public UpdateBoardDto update(Integer boardId, UpdateBoardDto updateBoardDto, User user) {
        Board boardToUpdateOpt = this.findBoardByIdElseThrow(boardId);

        if (user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("User does not have permission to update this board!");
        }

        boardToUpdateOpt.setTitle(updateBoardDto.getTitle());
        boardToUpdateOpt.setDescription(updateBoardDto.getDescription());

        Board boardUpdated = this.boardRepository.save(boardToUpdateOpt);

        return new UpdateBoardDto(boardUpdated.getTitle(), boardUpdated.getDescription());
    }

    public void delete(Integer boardId, User user) {
        Board boardToDeleteOpt = this.findBoardByIdElseThrow(boardId);

        if (user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("User does not have permission to update this board!");
        }

        this.boardRepository.delete(boardToDeleteOpt);
    }

    public List<User> findUsers(Integer boardId, User user) {
        this.findBoardByIdElseThrow(boardId);

        this.findUserAssociatedBoardElseThrow(boardId, user.getId());

        List<BoardMembers> boardMembersList = this.boardMemberRepository.findByBoardId(boardId);

        return boardMembersList.stream().map(BoardMembers::getUser).collect(Collectors.toList());
    }

    public void associateUserToBoard(User user, Board board, Role role) {
        BoardMembers boardMember = BoardMembers.builder()
                .user(user)
                .board(board)
                .role(role)
                .build();

        this.boardMemberRepository.save(boardMember);
    }
}
