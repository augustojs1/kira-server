package com.augustodev.kiraserver.modules.status.entities;

import com.augustodev.kiraserver.modules.boards.entities.Board;
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
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "position", nullable = false)
    private Integer position;

    @UpdateTimestamp(source = SourceType.DB)
    private Instant updatedAt;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdAt;
}
