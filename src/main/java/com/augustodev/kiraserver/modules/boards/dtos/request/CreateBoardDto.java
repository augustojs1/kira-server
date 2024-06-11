package com.augustodev.kiraserver.modules.boards.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateBoardDto {
    @NotNull(message = "title should not be null.")
    @NotEmpty(message = "title should not be an empty string.")
    private String title;

    @NotNull(message = "description should not be null.")
    @NotEmpty(message = "description should not be an empty string.")
    private String description;
}
