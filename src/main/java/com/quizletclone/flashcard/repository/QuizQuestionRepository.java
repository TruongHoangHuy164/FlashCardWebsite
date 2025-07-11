package com.quizletclone.flashcard.repository;

import com.quizletclone.flashcard.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {
    List<QuizQuestion> findByQuizId(Integer quizId);
} 