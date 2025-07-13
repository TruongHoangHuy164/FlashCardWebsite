package com.quizletclone.flashcard.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlHelper {

    public static String redirectWithMessage(String path, String key, String message) {
        return "redirect:" + path + "?" + key + "=" + encode(message);
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
