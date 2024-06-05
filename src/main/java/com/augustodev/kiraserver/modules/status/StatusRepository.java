package com.augustodev.kiraserver.modules.status;

import com.augustodev.kiraserver.modules.status.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> findById(Integer statusId);
    List<Status> findAllByBoardIdOrderByPosition(Integer boardId);
    Integer countByBoardId(Integer board_id);
    @Query(
            value = "SELECT s.*" +
                    "FROM status AS s " +
                    "INNER JOIN tasks AS t " +
                    "ON t.status_id = s.id " +
                    "WHERE t.status_id = :statusId",
            nativeQuery = true
    )
    List<Status> findTasksByStatusId(@Param("statusId") Integer statusId);
}
