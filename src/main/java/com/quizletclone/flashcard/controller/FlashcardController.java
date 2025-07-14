package com.quizletclone.flashcard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quizletclone.flashcard.model.Deck;
import com.quizletclone.flashcard.model.Flashcard;
import com.quizletclone.flashcard.repository.DeckRepository;
import com.quizletclone.flashcard.service.FlashcardService;

@Controller
@RequestMapping("/flashcards")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    @Autowired
    private DeckRepository deckRepository; // Để load danh sách Deck

    // Form tạo mới Flashcard
    @GetMapping("/create")
    public String showCreateForm(@RequestParam("deckId") Integer deckId, Model model) {
        Flashcard flashcard = new Flashcard();
        Deck deck = deckRepository.findById(deckId).orElseThrow(() -> new IllegalArgumentException("Invalid deck ID"));
        flashcard.setDeck(deck);

        model.addAttribute("flashcard", flashcard);
        return "flashcards/flashcard-add";
    }

    @PostMapping("/create")
    public String createFlashcard(@ModelAttribute Flashcard flashcard,
            @RequestParam("deckId") Integer deckId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {
        // Sửa ở đây dùng deckRepository thay vì deckService
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deck ID"));

        flashcard.setDeck(deck); // Gán deck để tránh lỗi null

        // TODO: xử lý ảnh nếu có

        flashcardService.save(flashcard);

        redirectAttributes.addFlashAttribute("success", "Đã thêm thẻ thành công!");
        return "redirect:/decks/" + deckId;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Flashcard flashcard = flashcardService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid flashcard ID"));

        model.addAttribute("flashcard", flashcard);
        return "flashcards/flashcard-edit"; // <--- sử dụng edit.html
    }

    @PostMapping("/edit/{id}")
    public String updateFlashcard(@PathVariable("id") Integer id, @ModelAttribute("flashcard") Flashcard updatedCard) {
        Flashcard existing = flashcardService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid flashcard ID"));

        existing.setTerm(updatedCard.getTerm());
        existing.setDefinition(updatedCard.getDefinition());
        existing.setImageUrl(updatedCard.getImageUrl());

        flashcardService.save(existing);

        return "redirect:/decks/" + existing.getDeck().getId();
    }

    // ==== XOÁ ====
    @PostMapping("/delete/{id}")
    public String deleteFlashcard(@PathVariable("id") Integer id) {
        Flashcard flashcard = flashcardService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid flashcard ID"));

        Integer deckId = flashcard.getDeck().getId();
        flashcardService.deleteById(id);

        return "redirect:/decks/" + deckId;
    }
}
