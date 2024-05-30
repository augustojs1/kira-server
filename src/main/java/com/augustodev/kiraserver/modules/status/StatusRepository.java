package com.augustodev.kiraserver.modules.status;

import com.augustodev.kiraserver.modules.status.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
