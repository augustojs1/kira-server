package com.augustodev.kiraserver.modules.tasks.mapper;


import com.augustodev.kiraserver.modules.tasks.dtos.response.TasksResponseSlimDto;
import com.augustodev.kiraserver.modules.tasks.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
