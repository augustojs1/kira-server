package com.augustodev.kiraserver.modules.boards;

import com.augustodev.kiraserver.modules.boards.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Optional<Board> findByTitle(String title);
}
