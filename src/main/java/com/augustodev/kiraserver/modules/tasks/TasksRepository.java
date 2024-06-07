package com.augustodev.kiraserver.modules.tasks;

import com.augustodev.kiraserver.modules.status.entities.Status;
import com.augustodev.kiraserver.modules.tasks.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findById(Integer taskId);
    List<Task> findByAssignedId(Integer boardId);
    List<Task> findByBoardId(Integer boardId);
    List<Task> findByStatusId(Integer statusId);
//    @Query(
//            value = "SELECT s.*" +
//                    "FROM status AS s " +
//                    "INNER JOIN tasks AS t " +
//                    "ON t.status_id = s.id " +
//                    "WHERE t.status_id = :statusId",
//            nativeQuery = true
//    )
//    List<Object> findFullTaskById(@Param("taskId") Integer taskId);
}
