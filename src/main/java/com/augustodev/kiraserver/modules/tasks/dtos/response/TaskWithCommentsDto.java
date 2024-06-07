package com.augustodev.kiraserver.modules.tasks.dtos.response;

import com.augustodev.kiraserver.modules.tasks.models.TaskCommentSlim;
import com.augustodev.kiraserver.modules.tasks.models.TaskSlim;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TaskWithCommentsDto {
    private TaskSlim task;
    private List<TaskCommentSlim> comments;
}
