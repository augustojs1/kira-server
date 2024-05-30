package com.augustodev.kiraserver.modules.status.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateStatusResponseDto {
    private Integer id;
    private String title;
    private Integer position;
}
