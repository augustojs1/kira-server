package com.augustodev.kiraserver.modules.tasks_comments.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TaskCommentSlimDto {
    private Integer id;
    private Integer userId;
    private Integer taskId;
    private String content;
    private Instant updatedAt;
    private Instant createdAt;
}
