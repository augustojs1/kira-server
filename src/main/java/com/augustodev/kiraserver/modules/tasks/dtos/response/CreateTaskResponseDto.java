package com.augustodev.kiraserver.modules.tasks.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateTaskResponseDto {
    private String title;
    private String description;
}
