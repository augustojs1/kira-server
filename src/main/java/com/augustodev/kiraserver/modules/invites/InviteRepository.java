package com.augustodev.kiraserver.modules.invites;

import com.augustodev.kiraserver.modules.invites.entities.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Integer> {
    Optional<Invite> findById(Integer id);
    Optional<Invite> findByInvitedIdAndActiveIsTrue(Integer invitedId);
    Optional<Invite> findByInviterIdAndActiveIsTrue(Integer inviterId);
}
