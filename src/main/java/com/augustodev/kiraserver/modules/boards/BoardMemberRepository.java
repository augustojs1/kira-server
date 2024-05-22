package com.augustodev.kiraserver.modules.boards;

import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardMemberRepository extends JpaRepository<BoardMembers, Integer> {
}
