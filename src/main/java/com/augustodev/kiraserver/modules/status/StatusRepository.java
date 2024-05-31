package com.augustodev.kiraserver.modules.status;

import com.augustodev.kiraserver.modules.status.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    List<Status> findAllByBoardIdOrderByPosition(Integer boardId);
    Integer countByBoardId(Integer board_id);
}
