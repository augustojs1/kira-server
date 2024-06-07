package com.augustodev.kiraserver.modules.tasks_comments.mapper;

import com.augustodev.kiraserver.modules.tasks_comments.dtos.response.TaskCommentSlimDto;
import com.augustodev.kiraserver.modules.tasks_comments.entities.TaskComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TasksCommentsMapper {
    public TaskCommentSlimDto map(TaskComment taskComment) {
        return new TaskCommentSlimDto(
                taskComment.getId(),
                taskComment.getUser().getId(),
                taskComment.getTask().getId(),
                taskComment.getContent(),
                taskComment.getUpdatedAt(),
                taskComment.getCreatedAt()
        );


    }
}
