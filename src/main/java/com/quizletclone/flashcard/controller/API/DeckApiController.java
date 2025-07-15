package com.quizletclone.flashcard.controller.API;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizletclone.flashcard.dto.DeckDto;
import com.quizletclone.flashcard.model.Deck;
import com.quizletclone.flashcard.model.User;
import com.quizletclone.flashcard.service.DeckService;
import com.quizletclone.flashcard.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/decks")
@RequiredArgsConstructor
public class DeckApiController {

    private final DeckService deckService;
    private final UserService userService;

    // GET: /api/decks
    @GetMapping
    public ResponseEntity<List<DeckDto>> getAllDecks() {
        List<DeckDto> result = deckService.findPublicDecks().stream()
                .map(DeckDto::new)
                .toList();
        return ResponseEntity.ok(result);
    }

    // GET: /api/decks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getDeckById(@PathVariable Integer id) {
        return deckService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: /api/decks
    @PostMapping
    public ResponseEntity<?> createDeck(@RequestBody Deck deck) {
        try {
            // Kiểm tra user có hợp lệ không
            if (deck.getUser() == null || deck.getUser().getId() == null) {
                return ResponseEntity.badRequest().body("Thiếu thông tin user hoặc user_id");
            }

            // Lấy user từ DB
            Optional<User> userOpt = userService.findById(deck.getUser().getId());
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("User không tồn tại");
            }

            deck.setUser(userOpt.get());
            deckService.save(deck);
            return ResponseEntity.ok("Tạo deck thành công");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi: " + e.getMessage());
        }
    }

    // PUT: /api/decks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeck(@PathVariable Integer id, @RequestBody Deck updatedDeck) {
        Optional<Deck> existingDeckOpt = deckService.findById(id);
        if (existingDeckOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Deck existingDeck = existingDeckOpt.get();
        updatedDeck.setId(id);
        updatedDeck.setUser(existingDeck.getUser());
        updatedDeck.setCreatedAt(existingDeck.getCreatedAt());

        Deck saved = deckService.save(updatedDeck);
        return ResponseEntity.ok(saved);
    }

    // DELETE: /api/decks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeck(@PathVariable Integer id) {
        if (deckService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        deckService.deleteById(id);
        return ResponseEntity.ok("Xóa thành công");
    }
}
