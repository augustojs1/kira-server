package com.augustodev.kiraserver.modules.status.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class StatusResponseDto {
    private Integer id;
    private String title;
    private Integer position;
    private Instant updatedAt;
    private Instant createdAt;
}
