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
public class TaskSlim {
    private Integer id;
    private Integer assignedId;
    private Integer boardId;
    private String statusName;
    private String title;
    private String description;
    private Instant updatedAt;
    private Instant createdAt;
}
