package com.augustodev.kiraserver.modules.tasks.entities;

import com.augustodev.kiraserver.modules.boards.entities.Board;
import com.augustodev.kiraserver.modules.status.entities.Status;
import com.augustodev.kiraserver.modules.users.entities.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_id", nullable = true)
    private User assigned;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @UpdateTimestamp(source = SourceType.DB)
    private Instant updatedAt;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdAt;
}
