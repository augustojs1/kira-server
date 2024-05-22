package com.augustodev.kiraserver.modules.boards;

import com.augustodev.kiraserver.common.exceptions.BadRequestException;
import com.augustodev.kiraserver.modules.boards.dtos.BoardCreatedDto;
import com.augustodev.kiraserver.modules.boards.dtos.CreateBoardDto;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import com.augustodev.kiraserver.modules.users.entities.User;
import com.augustodev.kiraserver.modules.users.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
