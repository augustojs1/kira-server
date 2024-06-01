package com.augustodev.kiraserver.modules.tasks.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateTaskDto {
    private Integer status_id;
    private String title;
    private String description;
}
