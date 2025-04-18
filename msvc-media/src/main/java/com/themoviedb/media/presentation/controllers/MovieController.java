package com.themoviedb.media.presentation.controllers;

import com.themoviedb.media.dto.MovieListDto;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/media/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/popular")
    public ResponseEntity<MovieListDto> getPopularMovies(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(movieService.getPopularMedia(page, language));
    }

}
