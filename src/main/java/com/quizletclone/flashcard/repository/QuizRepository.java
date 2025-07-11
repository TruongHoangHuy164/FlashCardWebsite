package com.quizletclone.flashcard.repository;

import com.quizletclone.flashcard.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    List<Quiz> findByUserId(Integer userId);
    List<Quiz> findByDeckId(Integer deckId);
} 