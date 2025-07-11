package com.quizletclone.flashcard.repository;

import com.quizletclone.flashcard.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Integer> {
    List<Deck> findByUserId(Integer userId);
    List<Deck> findByIsPublicTrue();
} 