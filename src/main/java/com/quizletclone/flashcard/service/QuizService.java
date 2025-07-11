package com.quizletclone.flashcard.service;

import com.quizletclone.flashcard.model.Quiz;
import com.quizletclone.flashcard.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> findByUserId(Integer userId) {
        return quizRepository.findByUserId(userId);
    }

    public List<Quiz> findByDeckId(Integer deckId) {
        return quizRepository.findByDeckId(deckId);
    }

    public Optional<Quiz> findById(Integer id) {
        return quizRepository.findById(id);
    }

    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }
} 