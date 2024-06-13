package com.augustodev.kiraserver.modules.tasks.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateTaskDto {
    @NotNull(message = "statusId is required.")
    private Integer status_id;

    @NotNull(message = "title is required.")
    @NotBlank(message = "title should not be an empty string.")
    private String title;

    @NotNull(message = "description is required.")
    @NotBlank(message = "description should not be an empty string.")
    private String description;
}
