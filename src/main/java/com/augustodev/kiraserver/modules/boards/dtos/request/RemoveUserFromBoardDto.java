package com.augustodev.kiraserver.modules.boards.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RemoveUserFromBoardDto {
    private Integer userId;
    private Integer memberId;
    private Integer boardId;
}
