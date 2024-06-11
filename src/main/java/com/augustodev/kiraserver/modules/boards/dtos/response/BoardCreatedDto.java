package com.augustodev.kiraserver.modules.boards.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BoardCreatedDto {
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
}
