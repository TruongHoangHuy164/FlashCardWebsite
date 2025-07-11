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
    public String listDecks(Model model) {
        List<Deck> decks = deckService.findPublicDecks();
        model.addAttribute("decks", decks);
        return "deck-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("deck", new Deck());
        return "deck-create";
    }

    @PostMapping("/create")
    public String createDeck(@ModelAttribute Deck deck) {
        // TODO: Lấy user từ session, tạm thời lấy user đầu tiên
        Optional<User> userOpt = userService.findById(1);
        userOpt.ifPresent(deck::setUser);
        deck.setIsPublic(true);
        deckService.save(deck);
        return "redirect:/decks";
    }

    @GetMapping("/{id}")
    public String viewDeck(@PathVariable Integer id, Model model) {
        Deck deck = deckService.findById(id).orElse(null);
        model.addAttribute("deck", deck);
        return "deck-detail";
    }
} 