package com.quizletclone.flashcard.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quizletclone.flashcard.model.Deck;
import com.quizletclone.flashcard.model.User;
import com.quizletclone.flashcard.service.DeckService;
import com.quizletclone.flashcard.service.UserService;
import static com.quizletclone.flashcard.util.UrlHelper.redirectWithMessage;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/decks")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;
    private final UserService userService;

    @GetMapping
    public String listDecks(@RequestParam(required = false) String success,
            @RequestParam(required = false) String error,
            Model model) {
        try {
            List<Deck> decks = deckService.findPublicDecks();
            model.addAttribute("decks", decks);

            if (success != null) {
                model.addAttribute("success", success);
            }
            if (error != null) {
                model.addAttribute("error", error);
            }

            return "decks/deck-list";
        } catch (Exception e) {
            return redirectWithMessage("/decks", "error", "Có lỗi xảy ra khi tải danh sách bộ thẻ: " + e.getMessage());
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("deck", new Deck());
        return "decks/deck-create";
    }

    @PostMapping("/create")
    public String createDeck(@ModelAttribute Deck deck) {
        try {
            Optional<User> userOpt = userService.findById(1); // Tạm thời dùng user cố định
            if (userOpt.isPresent()) {
                deck.setUser(userOpt.get());
                deck.setIsPublic(true);
                deckService.save(deck);
                return redirectWithMessage("/decks", "success", "Bộ thẻ đã được tạo thành công!");
            } else {
                return redirectWithMessage("/decks", "error", "Không tìm thấy người dùng!");
            }
        } catch (Exception e) {
            return redirectWithMessage("/decks", "error", "Có lỗi xảy ra khi tạo bộ thẻ: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public String viewDeck(@PathVariable Integer id, Model model) {
        try {
            Optional<Deck> deckOpt = deckService.findById(id);
            if (deckOpt.isPresent()) {
                model.addAttribute("deck", deckOpt.get());
                return "decks/deck-detail";
            } else {
                return redirectWithMessage("/decks", "error", "Không tìm thấy bộ thẻ!");
            }
        } catch (Exception e) {
            return redirectWithMessage("/decks", "error", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/edit")
    public String editDeck(@PathVariable Integer id, Model model) {
        try {
            Optional<Deck> deckOpt = deckService.findById(id);
            if (deckOpt.isPresent()) {
                model.addAttribute("deck", deckOpt.get());
                return "decks/deck-edit";
            } else {
                return redirectWithMessage("/decks", "error", "Không tìm thấy bộ thẻ!");
            }
        } catch (Exception e) {
            return redirectWithMessage("/decks", "error", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/edit")
    public String updateDeck(@PathVariable Integer id,
            @ModelAttribute Deck deck,
            Principal principal) {
        try {
            // Lấy deck cũ để lấy thông tin user
            Optional<Deck> existingDeckOpt = deckService.findById(id);
            if (existingDeckOpt.isEmpty()) {
                return redirectWithMessage("/decks", "error", "Không tìm thấy bộ thẻ!");
            }

            Deck existingDeck = existingDeckOpt.get();

            // Giữ lại user cũ
            deck.setUser(existingDeck.getUser());
            deck.setCreatedAt(existingDeck.getCreatedAt());
            deck.setId(id); // đảm bảo giữ đúng ID

            deckService.save(deck);
            return redirectWithMessage("/decks", "success", "Cập nhật thành công!");
        } catch (Exception e) {
            return redirectWithMessage("/decks", "error", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteDeck(@PathVariable Integer id) {
        try {
            if (deckService.findById(id).isPresent()) {
                deckService.deleteById(id);
                return redirectWithMessage("/decks", "success", "Bộ thẻ đã được xóa thành công!");
            } else {
                return redirectWithMessage("/decks", "error", "Không tìm thấy bộ thẻ!");
            }
        } catch (Exception e) {
            return redirectWithMessage("/decks", "error", "Có lỗi xảy ra khi xóa: " + e.getMessage());
        }
    }
}
