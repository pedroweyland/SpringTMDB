package com.themoviedb.media.service.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class LanguageValidator {

    private static final Set<String> SUPPORTED_LANGUAGES = Set.of("en", "es", "fr", "zh", "ja", "ru");


    public static void validateLanguage(String language) {

        if (language == null || language.isBlank()) {
            throw new IllegalArgumentException("Invalid language: " + language);
        }

        String normalized = language.toLowerCase();

        if (!SUPPORTED_LANGUAGES.contains(normalized)) {
            throw new IllegalArgumentException("Invalid language: " + language);
        }
    }
}
