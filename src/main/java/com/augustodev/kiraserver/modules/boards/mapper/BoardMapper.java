package com.augustodev.kiraserver.modules.boards.mapper;

import com.augustodev.kiraserver.modules.boards.dtos.response.BoardDTO;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class BoardMapper {
    public List<BoardDTO> boardsToSlimDto(Optional<BoardMembers> boardMembers) {

        if (boardMembers == null) {
            return new ArrayList<BoardDTO>() ;
        }

        return boardMembers.stream().map(
                boardMember -> BoardDTO.builder()
                        .id(boardMember.getBoard().getId())
                        .title(boardMember.getBoard().getTitle())
                        .description(boardMember.getBoard().getDescription())
                        .updatedAt(boardMember.getBoard().getUpdatedAt())
                        .createdAt(boardMember.getBoard().getCreatedAt())
                        .build()
                )
                .toList();
    }
}
