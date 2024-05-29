package com.augustodev.kiraserver.modules.boards.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BoardDTO {
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private Instant updatedAt;
    private Instant createdAt;
}
