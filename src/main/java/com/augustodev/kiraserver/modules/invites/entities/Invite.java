package com.augustodev.kiraserver.modules.invites.entities;

import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.users.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invites")
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "inviter_id")
    private User inviter;

    @OneToOne
    @JoinColumn(name = "invited_id")
    private User invited;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "accepted")
    private Boolean accepted;

    @UpdateTimestamp(source = SourceType.DB)
    private Instant updatedAt;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdAt;
}
