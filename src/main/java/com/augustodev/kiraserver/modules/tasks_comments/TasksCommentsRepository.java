package com.augustodev.kiraserver.modules.tasks_comments;

import com.augustodev.kiraserver.modules.tasks_comments.entities.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksCommentsRepository  extends JpaRepository<TaskComment, Integer> {
    List<TaskComment> findByTaskId(Integer taskId);
}
