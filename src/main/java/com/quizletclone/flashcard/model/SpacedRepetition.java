package com.quizletclone.flashcard.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "spaced_repetition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpacedRepetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_id", nullable = false)
    private Flashcard flashcard;

    private LocalDateTime lastReviewed;
    private LocalDateTime nextReview;
    private Integer intervalDays;
    private Float easeFactor;
} 