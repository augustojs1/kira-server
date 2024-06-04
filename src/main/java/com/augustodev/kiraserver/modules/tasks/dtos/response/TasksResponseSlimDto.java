package com.augustodev.kiraserver.modules.tasks.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TasksResponseSlimDto {
    private Integer id;
    private String title;
    private String description;
    private Integer statusId;
    private String statusName;
    private Integer assignedId;
    private String assignedName;
    private Instant updatedAt;
    private Instant createdAt;
}
