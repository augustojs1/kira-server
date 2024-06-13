package com.augustodev.kiraserver.modules.tasks_comments.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateTaskCommentDto {
    @NotNull(message = "content is required.")
    @NotBlank(message = "content should not be an empty string.")
    private String content;
}
