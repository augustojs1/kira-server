package com.augustodev.kiraserver.modules.tasks;

import com.augustodev.kiraserver.modules.tasks.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TasksRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findById(Integer taskId);
}
