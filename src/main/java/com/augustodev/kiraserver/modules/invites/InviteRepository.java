package com.augustodev.kiraserver.modules.invites;

import com.augustodev.kiraserver.modules.invites.entities.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Integer> {
    Optional<Invite> findByInvitedId(Integer invitedId);
}
