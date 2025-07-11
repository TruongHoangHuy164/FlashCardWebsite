package com.quizletclone.flashcard.controller;

import com.quizletclone.flashcard.model.User;
import com.quizletclone.flashcard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (userService.findByUsername(user.getUsername()).isPresent() || userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Tên đăng nhập hoặc email đã tồn tại!");
            return "register";
        }
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Đơn giản: chỉ hiển thị profile, chưa xử lý session
    @GetMapping("/profile")
    public String showProfile(Model model) {
        // TODO: Lấy user từ session
        model.addAttribute("user", null);
        return "profile";
    }
} 