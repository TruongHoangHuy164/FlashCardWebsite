package com.quizletclone.flashcard.repository;

import com.quizletclone.flashcard.model.SpacedRepetition;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpacedRepetitionRepository extends JpaRepository<SpacedRepetition, Integer> {
    List<SpacedRepetition> findByUserId(Integer userId);
    List<SpacedRepetition> findByFlashcardId(Integer flashcardId);
} 