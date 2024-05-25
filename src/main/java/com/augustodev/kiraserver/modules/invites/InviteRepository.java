package com.augustodev.kiraserver.modules.invites;

import com.augustodev.kiraserver.modules.invites.entities.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, Integer> {
}
