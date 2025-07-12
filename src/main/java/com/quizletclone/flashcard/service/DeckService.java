package com.quizletclone.flashcard.service;

import com.quizletclone.flashcard.model.Deck;
import com.quizletclone.flashcard.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DeckService {
    @Autowired
    private DeckRepository deckRepository;

    public List<Deck> findByUserId(Integer userId) {
        return deckRepository.findByUserId(userId);
    }

    public List<Deck> findPublicDecks() {
        return deckRepository.findByIsPublicTrue();
    }

    public Optional<Deck> findById(Integer id) {
        return deckRepository.findById(id);
    }

    public Deck save(Deck deck) {
        return deckRepository.save(deck);
    }

    public void deleteById(Integer id) {
        deckRepository.deleteById(id);
    }
} 