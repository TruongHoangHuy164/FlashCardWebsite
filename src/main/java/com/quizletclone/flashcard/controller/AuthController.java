package com.quizletclone.flashcard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quizletclone.flashcard.model.User;
import com.quizletclone.flashcard.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            // Kiểm tra username đã tồn tại
            if (userService.findByUsername(user.getUsername()).isPresent()) {
                model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
                return "auth/register";
            }

            // Kiểm tra email đã tồn tại
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                model.addAttribute("error", "Email đã tồn tại!");
                return "auth/register";
            }

            // Lưu user mới
            userService.save(user);
            return "redirect:/login?success=Đăng ký thành công! Vui lòng đăng nhập.";

        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi đăng ký: " + e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String success, Model model) {
        if (success != null) {
            model.addAttribute("success", success);
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            // Tìm user trong database
            var userOpt = userService.findByUsername(username);

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                // Kiểm tra password (trong thực tế nên hash password)
                if (user.getPassword().equals(password)) {
                    // TODO: Tạo session cho user
                    return "redirect:/decks";
                } else {
                    model.addAttribute("error", "Mật khẩu không đúng!");
                }
            } else {
                model.addAttribute("error", "Tên đăng nhập không tồn tại!");
            }

            return "auth/login";

        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi đăng nhập: " + e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        // TODO: Xóa session
        return "redirect:/";
    }
}
