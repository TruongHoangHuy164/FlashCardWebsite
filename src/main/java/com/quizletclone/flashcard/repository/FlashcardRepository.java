package com.quizletclone.flashcard.repository;

import com.quizletclone.flashcard.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
    List<Flashcard> findByDeckId(Integer deckId);
} 