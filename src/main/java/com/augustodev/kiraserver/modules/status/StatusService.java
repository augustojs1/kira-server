package com.augustodev.kiraserver.modules.status;

import com.augustodev.kiraserver.modules.boards.BoardService;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.status.dtos.request.CreateStatusDto;
import com.augustodev.kiraserver.modules.status.dtos.response.CreateStatusResponseDto;
import com.augustodev.kiraserver.modules.status.entities.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final BoardService boardService;
    private final StatusRepository statusRepository;

    public CreateStatusResponseDto create(CreateStatusDto createStatusDto, Integer userId) {
        Board board = this.boardService.findBoardByIdElseThrow(createStatusDto.getBoardId());

        this.boardService.checkIfUserIsBoardAdminElseThrow(board.getId(), userId);

        Status status = Status.builder()
                .title(createStatusDto.getTitle())
                .board(board)
                .position(1)
                .build();

        Status createdStatus = this.statusRepository.save(status);

        return  CreateStatusResponseDto.builder()
                .id(createdStatus.getId())
                .position(status.getPosition())
                .title(createdStatus.getTitle())
                .build();
    }
}
