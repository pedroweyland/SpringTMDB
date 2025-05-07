package com.themoviedb.media.service.utils;

import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PageNotFoundException;
import feign.FeignException;

import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractMediaService<T> {

    private final Set<String> SUPPORTED_LANGUAGES = Set.of("en", "es", "fr", "zh", "ja", "ru");

    protected void validateLanguage(String language) {

        if (language == null || language.isBlank()) {
            throw new LanguagueNotFoundException("Invalid language: " + language);
        }

        String normalized = language.toLowerCase();

        if (!SUPPORTED_LANGUAGES.contains(normalized)) {
            throw new LanguagueNotFoundException("Invalid language: " + language);
        }
    }

    protected T executeWithHandling(Supplier<T> action, Integer page) {
        try {
            return action.get();
        } catch (FeignException e) {
            System.out.println(e.getMessage());
            System.out.println("--------");
            System.out.println(e.status());
            if (e.status() == 400) {
                throw new PageNotFoundException("Page not found: " + page);
            }
            throw new RuntimeException("Something went wrong: " + e.getMessage());
        }
    }

    protected void validatePageContent(Integer totalPages, Integer page) {
        // Valido esto aparte porque hay veces que me trae el objeto pero sin una lista de peliculas
        if (totalPages < page) {
            throw new PageNotFoundException("Page not found: " + page);
        }
    }

    protected void validateSearchQuery(Boolean listIsEmpty, String query) {

        if (listIsEmpty) {
            throw new IllegalArgumentException("No results found" + (query != null ? " for query: " + query : ""));
        }

    }


}
