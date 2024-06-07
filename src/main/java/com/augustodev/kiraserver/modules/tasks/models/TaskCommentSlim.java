package com.augustodev.kiraserver.modules.tasks.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TaskCommentSlim {
    private Integer id;
    private Integer ownerId;
    private String content;
    private Instant updatedAt;
    private Instant createdAt;
}
