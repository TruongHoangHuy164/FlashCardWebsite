package com.quizletclone.flashcard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.quizletclone.flashcard.model.User;
import com.quizletclone.flashcard.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public String showProfile(Model model) {
        try {
            // TODO: Lấy user từ session
            // Tạm thời lấy user đầu tiên trong database
            var userOpt = userService.findById(1);
            if (userOpt.isPresent()) {
                model.addAttribute("user", userOpt.get());
            } else {
                // Tạo user mẫu nếu không có user nào
                User sampleUser = new User();
                sampleUser.setId(1);
                sampleUser.setUsername("demo_user");
                sampleUser.setEmail("demo@example.com");
                sampleUser.setAvatarUrl("https://via.placeholder.com/100x100/667eea/ffffff?text=U");
                model.addAttribute("user", sampleUser);
            }
            return "profile";

        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "profile";
        }
    }
}