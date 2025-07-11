package com.quizletclone.flashcard.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "flashcards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String term;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String definition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    private String imageUrl;
} 