package com.augustodev.kiraserver.modules.status;

import com.augustodev.kiraserver.common.exceptions.BadRequestException;
import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundException;
import com.augustodev.kiraserver.common.exceptions.UnauthorizedException;
import com.augustodev.kiraserver.modules.boards.BoardService;
import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import com.augustodev.kiraserver.modules.status.dtos.request.ChangeStatusPositionDto;
import com.augustodev.kiraserver.modules.status.dtos.request.CreateStatusDto;
import com.augustodev.kiraserver.modules.status.dtos.response.CreateStatusResponseDto;
import com.augustodev.kiraserver.modules.status.dtos.response.StatusResponseDto;
import com.augustodev.kiraserver.modules.status.entities.Status;
import com.augustodev.kiraserver.modules.users.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final BoardService boardService;
    private final StatusRepository statusRepository;

    public Status findStatusByIdElseThrow(Integer statusId) {
        Optional<Status> statusOpt = this.statusRepository.findById(statusId);

        if (statusOpt.isEmpty()) {
            throw new ResourceNotFoundException("Status with this id not found!");
        }

        Status status = statusOpt.get();

        return status;
    }

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

    public void changePosition(ChangeStatusPositionDto changeStatusPositionDto, Integer userId) {
        this.boardService.findBoardByIdElseThrow(changeStatusPositionDto.getBoardId());

        BoardMembers boardMembers = this.boardService.findUserAssociatedBoardElseThrow(
                changeStatusPositionDto.getBoardId(),
                userId
        );

        if (boardMembers.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("User not authorized for this action!");
        }

        Status statusOrigin = this.findStatusByIdElseThrow(changeStatusPositionDto.getStatusOriginId());

        Status statusDestination = this.findStatusByIdElseThrow(changeStatusPositionDto.getStatusOriginDestination());

        Integer originPositionTemp = statusOrigin.getPosition();

        statusOrigin.setPosition(statusDestination.getPosition());
        statusDestination.setPosition(originPositionTemp);

        this.statusRepository.save(statusOrigin);
        this.statusRepository.save(statusDestination);
    }
}
