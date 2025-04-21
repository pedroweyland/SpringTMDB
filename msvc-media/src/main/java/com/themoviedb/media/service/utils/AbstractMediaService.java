package com.themoviedb.media.service.utils;

import com.themoviedb.media.dto.MovieListDto;
import com.themoviedb.media.dto.SerieListDto;
import com.themoviedb.media.exception.PageNotFoundException;
import feign.FeignException;

import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractMediaService<T> {

    private final Set<String> SUPPORTED_LANGUAGES = Set.of("en", "es", "fr", "zh", "ja", "ru");

    protected void validateLanguage(String language) {

        if (language == null || language.isBlank()) {
            throw new IllegalArgumentException("Invalid language: " + language);
        }

        String normalized = language.toLowerCase();

        if (!SUPPORTED_LANGUAGES.contains(normalized)) {
            throw new IllegalArgumentException("Invalid language: " + language);
        }
    }

    protected T executeWithHandling(Supplier<T> action, String fallbackMessage) {
        try {
            return action.get();
        } catch (FeignException e) {
            if (e.status() == 400 || e.status() == 404) {
                throw new PageNotFoundException(fallbackMessage);
            }
            throw new RuntimeException("Something went wrong: " + e.getMessage());
        }
    }



}
