package com.augustodev.kiraserver.modules.status;

import com.augustodev.kiraserver.common.exceptions.BadRequestException;
import com.augustodev.kiraserver.modules.boards.BoardService;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.status.dtos.request.CreateStatusDto;
import com.augustodev.kiraserver.modules.status.dtos.response.CreateStatusResponseDto;
import com.augustodev.kiraserver.modules.status.dtos.response.StatusResponseDto;
import com.augustodev.kiraserver.modules.status.entities.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final BoardService boardService;
    private final StatusRepository statusRepository;

    private Integer statusCount(Integer boardId) {
        return this.statusRepository.countByBoardId(boardId);
    }

    public CreateStatusResponseDto create(CreateStatusDto createStatusDto, Integer userId) {
        Integer statusCount = this.statusCount(createStatusDto.getBoardId());

        if (statusCount == 8) {
            throw new BadRequestException("Max of 8 status per board reached.");
        }

        Integer nextPosition = statusCount + 1;

        Board board = this.boardService.findBoardByIdElseThrow(createStatusDto.getBoardId());

        this.boardService.checkIfUserIsBoardAdminElseThrow(board.getId(), userId);

        Status status = Status.builder()
                .title(createStatusDto.getTitle())
                .board(board)
                .position(nextPosition)
                .build();

        Status createdStatus = this.statusRepository.save(status);

        return  CreateStatusResponseDto.builder()
                .id(createdStatus.getId())
                .position(status.getPosition())
                .title(createdStatus.getTitle())
                .build();
    }

    public List<StatusResponseDto> findAllByBoardId(Integer boardId, Integer userId) {
        this.boardService.findBoardByIdElseThrow(boardId);

        this.boardService.findUserAssociatedBoardElseThrow(boardId, userId);

        List<Status> status = this.statusRepository.findAllByBoardIdOrderByPosition(boardId);

        return status.stream()
                .map(status1 -> new StatusResponseDto(
                        status1.getId(),
                        status1.getTitle(),
                        status1.getPosition(),
                        status1.getUpdatedAt(),
                        status1.getCreatedAt()
                )).toList();
    }
}
