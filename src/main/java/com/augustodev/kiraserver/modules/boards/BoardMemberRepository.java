package com.augustodev.kiraserver.modules.boards;

import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardMemberRepository extends JpaRepository<BoardMembers, Integer> {
    Optional<BoardMembers> findByBoardIdAndUserId(Integer boardId, Integer userId);
    List<BoardMembers> findByBoardId(Integer boardId);
    Optional<BoardMembers> findByUserId(Integer userId);
}
