package com.quizletclone.flashcard.controller;

import com.quizletclone.flashcard.model.Deck;
import com.quizletclone.flashcard.model.User;
import com.quizletclone.flashcard.service.DeckService;
import com.quizletclone.flashcard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
            // Lấy tất cả deck từ database
            List<Deck> decks = deckService.findPublicDecks();
            model.addAttribute("decks", decks);
            
            // Xử lý thông báo
            if (success != null) {
                model.addAttribute("success", success);
            }
            if (error != null) {
                model.addAttribute("error", error);
            }
            
            return "deck-list";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi tải danh sách bộ thẻ: " + e.getMessage());
            return "deck-list";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("deck", new Deck());
        return "deck-create";
    }

    @PostMapping("/create")
    public String createDeck(@ModelAttribute Deck deck) {
        try {
            // TODO: Lấy user từ session, tạm thời lấy user đầu tiên
            Optional<User> userOpt = userService.findById(1);
            if (userOpt.isPresent()) {
                deck.setUser(userOpt.get());
                deck.setIsPublic(true);
                deckService.save(deck);
                return "redirect:/decks?success=Bộ thẻ đã được tạo thành công!";
            } else {
                return "redirect:/decks?error=Không tìm thấy người dùng!";
            }
        } catch (Exception e) {
            return "redirect:/decks?error=Có lỗi xảy ra khi tạo bộ thẻ: " + e.getMessage();
        }
    }

    @GetMapping("/{id}")
    public String viewDeck(@PathVariable Integer id, Model model) {
        try {
            Optional<Deck> deckOpt = deckService.findById(id);
            if (deckOpt.isPresent()) {
                model.addAttribute("deck", deckOpt.get());
                return "deck-detail";
            } else {
                return "redirect:/decks?error=Không tìm thấy bộ thẻ!";
            }
        } catch (Exception e) {
            return "redirect:/decks?error=Có lỗi xảy ra: " + e.getMessage();
        }
    }

    @GetMapping("/{id}/edit")
    public String editDeck(@PathVariable Integer id, Model model) {
        try {
            Optional<Deck> deckOpt = deckService.findById(id);
            if (deckOpt.isPresent()) {
                model.addAttribute("deck", deckOpt.get());
                return "deck-create"; // Sử dụng lại form tạo
            } else {
                return "redirect:/decks?error=Không tìm thấy bộ thẻ!";
            }
        } catch (Exception e) {
            return "redirect:/decks?error=Có lỗi xảy ra: " + e.getMessage();
        }
    }

    @PostMapping("/{id}/edit")
    public String updateDeck(@PathVariable Integer id, @ModelAttribute Deck deck) {
        try {
            Optional<Deck> existingDeck = deckService.findById(id);
            if (existingDeck.isPresent()) {
                Deck existing = existingDeck.get();
                existing.setTitle(deck.getTitle());
                existing.setDescription(deck.getDescription());
                existing.setSubject(deck.getSubject());
                existing.setIsPublic(deck.getIsPublic());
                deckService.save(existing);
                return "redirect:/decks?success=Bộ thẻ đã được cập nhật thành công!";
            } else {
                return "redirect:/decks?error=Không tìm thấy bộ thẻ!";
            }
        } catch (Exception e) {
            return "redirect:/decks?error=Có lỗi xảy ra khi cập nhật: " + e.getMessage();
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteDeck(@PathVariable Integer id) {
        try {
            if (deckService.findById(id).isPresent()) {
                deckService.deleteById(id);
                return "redirect:/decks?success=Bộ thẻ đã được xóa thành công!";
            } else {
                return "redirect:/decks?error=Không tìm thấy bộ thẻ!";
            }
        } catch (Exception e) {
            return "redirect:/decks?error=Có lỗi xảy ra khi xóa: " + e.getMessage();
        }
    }
} 