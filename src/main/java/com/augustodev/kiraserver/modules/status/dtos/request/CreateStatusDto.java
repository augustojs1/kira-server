package com.augustodev.kiraserver.modules.status.dtos.request;

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
public class CreateStatusDto {
    @NotNull(message = "boardId is required.")
    private Integer boardId;

    @NotNull(message = "title is required.")
    @NotBlank(message = "title should not be an empty string.")
    private String title;
}
