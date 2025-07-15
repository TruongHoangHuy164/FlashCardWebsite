package com.quizletclone.flashcard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.quizletclone.flashcard.model.User;
import com.quizletclone.flashcard.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        try {
            // Lấy user từ session
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if (loggedInUser == null) {
                // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
                return "redirect:/login?error=Vui lòng đăng nhập để xem hồ sơ.";
            }

            // Optional: Lấy lại user từ DB để đảm bảo dữ liệu mới nhất (nếu cần)
            var userOpt = userService.findById(loggedInUser.getId());
            if (userOpt.isPresent()) {
                model.addAttribute("user", userOpt.get());
            } else {
                model.addAttribute("user", loggedInUser); // fallback từ session
            }

            return "user/profile";

        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "user/profile";
        }
    }
}