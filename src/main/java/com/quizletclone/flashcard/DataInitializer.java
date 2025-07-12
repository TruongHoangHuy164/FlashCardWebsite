package com.quizletclone.flashcard;

import com.quizletclone.flashcard.model.Deck;
import com.quizletclone.flashcard.model.User;
import com.quizletclone.flashcard.service.DeckService;
import com.quizletclone.flashcard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private DeckService deckService;

    @Override
    public void run(String... args) throws Exception {
        // Tạo user mẫu nếu chưa có
        if (userService.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword("admin");
            adminUser.setAvatarUrl("https://via.placeholder.com/100x100/667eea/ffffff?text=A");
            userService.save(adminUser);
            System.out.println("Đã tạo user admin");
        }

        if (userService.findByUsername("demo").isEmpty()) {
            User demoUser = new User();
            demoUser.setUsername("demo");
            demoUser.setEmail("demo@example.com");
            demoUser.setPassword("demo");
            demoUser.setAvatarUrl("https://via.placeholder.com/100x100/764ba2/ffffff?text=D");
            userService.save(demoUser);
            System.out.println("Đã tạo user demo");
        }

        // Tạo deck mẫu nếu chưa có
        if (deckService.findPublicDecks().isEmpty()) {
            User demoUser = userService.findByUsername("demo").orElse(
                userService.findByUsername("admin").orElse(null)
            );

            if (demoUser != null) {
                // Deck 1: Từ vựng Tiếng Anh
                Deck deck1 = new Deck();
                deck1.setTitle("Từ vựng Tiếng Anh cơ bản");
                deck1.setDescription("Học các từ vựng tiếng Anh cơ bản cho người mới bắt đầu");
                deck1.setSubject("Tiếng Anh");
                deck1.setUser(demoUser);
                deck1.setIsPublic(true);
                deck1.setCreatedAt(LocalDateTime.now().minusDays(5));
                deckService.save(deck1);

                // Deck 2: Công thức Toán học
                Deck deck2 = new Deck();
                deck2.setTitle("Công thức Toán học");
                deck2.setDescription("Tổng hợp các công thức toán học quan trọng");
                deck2.setSubject("Toán học");
                deck2.setUser(demoUser);
                deck2.setIsPublic(true);
                deck2.setCreatedAt(LocalDateTime.now().minusDays(3));
                deckService.save(deck2);

                // Deck 3: Lịch sử Việt Nam
                Deck deck3 = new Deck();
                deck3.setTitle("Lịch sử Việt Nam");
                deck3.setDescription("Các sự kiện lịch sử quan trọng của Việt Nam");
                deck3.setSubject("Lịch sử");
                deck3.setUser(demoUser);
                deck3.setIsPublic(false);
                deck3.setCreatedAt(LocalDateTime.now().minusDays(1));
                deckService.save(deck3);

                System.out.println("Đã tạo 3 deck mẫu");
            }
        }
    }
} 