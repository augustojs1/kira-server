package com.augustodev.kiraserver.modules.boards.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateBoardDto {
    private String title;
    private String description;
}
