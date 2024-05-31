package com.augustodev.kiraserver.modules.status;

import com.augustodev.kiraserver.modules.status.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> findById(Integer statusId);
    List<Status> findAllByBoardIdOrderByPosition(Integer boardId);
    Integer countByBoardId(Integer board_id);
}
