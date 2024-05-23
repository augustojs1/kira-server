package com.augustodev.kiraserver.modules.boards;

import com.augustodev.kiraserver.common.exceptions.BadRequestException;
import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundException;
import com.augustodev.kiraserver.common.exceptions.UnauthorizedException;
import com.augustodev.kiraserver.modules.boards.dtos.BoardCreatedDto;
import com.augustodev.kiraserver.modules.boards.dtos.CreateBoardDto;
import com.augustodev.kiraserver.modules.boards.dtos.UpdateBoardDto;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
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
        Optional<Board> boardToUpdateOpt = this.boardRepository.findById(boardId);

        if (boardToUpdateOpt.isEmpty()) {
            throw new ResourceNotFoundException("Board with this Id not found!");
        }

        if (user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("User does not have permission to update this board!");
        }

        Board board = boardToUpdateOpt.get();

        board.setTitle(updateBoardDto.getTitle());
        board.setDescription(updateBoardDto.getDescription());

        Board boardUpdated = this.boardRepository.save(board);

        return new UpdateBoardDto(boardUpdated.getTitle(), boardUpdated.getDescription());
    }

    public void delete(Integer boardId, User user) {
        Optional<Board> boardToDeleteOpt = this.boardRepository.findById(boardId);

        if (boardToDeleteOpt.isEmpty()) {
            throw new ResourceNotFoundException("Board with this Id not found!");
        }

        if (user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("User does not have permission to update this board!");
        }

        Board board = boardToDeleteOpt.get();

        this.boardRepository.delete(board);
    }

    public List<User> findUsers(Integer boardId, User user) {
        Optional<Board> boardToFind = this.boardRepository.findById(boardId);

        if (boardToFind.isEmpty()) {
            throw new ResourceNotFoundException("Board with this Id not found!");
        }

        Optional<BoardMembers> boardMembers = this.boardMemberRepository.findByBoardIdAndUserId(boardId, user.getId());

        if (boardMembers.isEmpty()) {
            throw new UnauthorizedException("User is not a member of this board!");
        }

        List<BoardMembers> boardMembersList = this.boardMemberRepository.findByBoardId(boardId);

        return boardMembersList.stream().map(BoardMembers::getUser).collect(Collectors.toList());
    }
}
