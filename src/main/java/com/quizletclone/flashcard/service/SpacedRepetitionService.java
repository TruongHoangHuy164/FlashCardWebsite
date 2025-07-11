package com.quizletclone.flashcard.service;

import com.quizletclone.flashcard.model.SpacedRepetition;
import com.quizletclone.flashcard.repository.SpacedRepetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SpacedRepetitionService {
    @Autowired
    private SpacedRepetitionRepository spacedRepetitionRepository;

    public List<SpacedRepetition> findByUserId(Integer userId) {
        return spacedRepetitionRepository.findByUserId(userId);
    }

    public List<SpacedRepetition> findByFlashcardId(Integer flashcardId) {
        return spacedRepetitionRepository.findByFlashcardId(flashcardId);
    }

    public Optional<SpacedRepetition> findById(Integer id) {
        return spacedRepetitionRepository.findById(id);
    }

    public SpacedRepetition save(SpacedRepetition spacedRepetition) {
        return spacedRepetitionRepository.save(spacedRepetition);
    }
} 