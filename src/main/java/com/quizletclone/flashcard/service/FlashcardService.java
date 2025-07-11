package com.quizletclone.flashcard.service;

import com.quizletclone.flashcard.model.Flashcard;
import com.quizletclone.flashcard.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {
    @Autowired
    private FlashcardRepository flashcardRepository;

    public List<Flashcard> findByDeckId(Integer deckId) {
        return flashcardRepository.findByDeckId(deckId);
    }

    public Optional<Flashcard> findById(Integer id) {
        return flashcardRepository.findById(id);
    }

    public Flashcard save(Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }
} 