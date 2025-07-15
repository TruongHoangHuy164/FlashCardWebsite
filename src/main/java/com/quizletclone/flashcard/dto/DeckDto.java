package com.quizletclone.flashcard.dto;

import com.quizletclone.flashcard.model.Deck;

import java.time.LocalDateTime;

public class DeckDto {
    private Integer id;
    private String title;
    private String description;
    private String subject;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private String username; // hoặc email, nếu cần

    public DeckDto(Deck deck) {
        this.id = deck.getId();
        this.title = deck.getTitle();
        this.description = deck.getDescription();
        this.subject = deck.getSubject();
        this.isPublic = deck.getIsPublic();
        this.createdAt = deck.getCreatedAt();
        try {
            this.username = deck.getUser() != null ? deck.getUser().getUsername() : null;
        } catch (Exception e) {
            this.username = null;
        }
    }

    // Getters (nếu không dùng Lombok)
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSubject() {
        return subject;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username;
    }
}
