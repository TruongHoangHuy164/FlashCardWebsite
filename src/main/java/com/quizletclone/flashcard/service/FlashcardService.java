package com.quizletclone.flashcard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizletclone.flashcard.model.Flashcard;
import com.quizletclone.flashcard.repository.FlashcardRepository;

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

    public Flashcard deleteById(Integer id) {
        Optional<Flashcard> optionalFlashcard = flashcardRepository.findById(id);

        if (optionalFlashcard.isPresent()) {
            Flashcard flashcard = optionalFlashcard.get();
            flashcardRepository.deleteById(id);
            return flashcard;
        } else {
            throw new IllegalArgumentException("Không tìm thấy flashcard với ID: " + id);
        }
    }
}