package com.augustodev.kiraserver.modules.tasks.mapper;


import com.augustodev.kiraserver.modules.tasks.dtos.response.TaskWithCommentsDto;
import com.augustodev.kiraserver.modules.tasks.dtos.response.TasksResponseSlimDto;
import com.augustodev.kiraserver.modules.tasks.entities.Task;
import com.augustodev.kiraserver.modules.tasks.models.TaskCommentSlim;
import com.augustodev.kiraserver.modules.tasks.models.TaskSlim;
import com.augustodev.kiraserver.modules.tasks_comments.entities.TaskComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class TaskMapper {
    public TasksResponseSlimDto map(Task task) {
        return new TasksResponseSlimDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().getId(),
                task.getStatus().getTitle(),
                task.getAssigned() == null ? null : task.getAssigned().getId(),
                task.getAssigned() == null ? null : task.getAssigned().getFirstName() + " " + task.getAssigned().getLastName(),
                task.getUpdatedAt(),
                task.getCreatedAt()
        );
    }
    public List<TasksResponseSlimDto> mapList(List<Task> tasks) {

        if (tasks.isEmpty()) {
            return List.of();
        }

        return tasks.stream()
                .map(task ->
                             new TasksResponseSlimDto(
                                     task.getId(),
                                     task.getTitle(),
                                     task.getDescription(),
                                     task.getStatus().getId(),
                                     task.getStatus().getTitle(),
                                     task.getAssigned() == null ? null : task.getAssigned().getId(),
                                     task.getAssigned() == null ? null : task.getAssigned().getFirstName() + " " + task.getAssigned().getLastName(),
                                     task.getUpdatedAt(),
                                     task.getCreatedAt()
                             )
                ).toList();
    }
    public TaskWithCommentsDto mapTaskWithComments(List<TaskComment> comment) {
        List<TaskCommentSlim> taskCommentSlimList = new ArrayList<TaskCommentSlim>();

       List<TaskWithCommentsDto> taskWithCommentsDtoList = comment.stream().map(taskComment -> {
                 TaskSlim taskSlim = new TaskSlim(
                         taskComment.getTask().getId(),
                         taskComment.getUser().getId(),
                         taskComment.getTask().getBoard().getId(),
                         taskComment.getTask().getStatus().getTitle(),
                         taskComment.getTask().getTitle(),
                         taskComment.getTask().getDescription(),
                         taskComment.getTask().getUpdatedAt(),
                         taskComment.getTask().getCreatedAt()
                 );
                 taskCommentSlimList.add(
                         new TaskCommentSlim(
                                 taskComment.getId(),
                                 taskComment.getUser().getId(),
                                 taskComment.getContent(),
                                 taskComment.getUpdatedAt(),
                                 taskComment.getCreatedAt()
                         )
                 );

                 return new TaskWithCommentsDto(taskSlim, taskCommentSlimList);
            }
        ).toList();

       return taskWithCommentsDtoList.get(0);
    }
}
