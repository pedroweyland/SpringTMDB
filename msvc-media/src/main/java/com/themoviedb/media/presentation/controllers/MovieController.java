package com.themoviedb.media.presentation.controllers;

import com.themoviedb.media.dto.MovieListDto;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/media/movie")
@RequiredArgsConstructor
public class MovieController implements IMediaController<MovieListDto> {

    private final MovieService movieService;

    @Override
    @GetMapping("/popular")
    public ResponseEntity<MovieListDto> getPopularMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(movieService.getPopularMedia(page, language));
    }

    @Override
    @GetMapping("/details/{idMovie}")
    public ResponseEntity<MovieListDto> getDetailsMedia(
        @PathVariable Integer idMovie,
        @RequestParam(defaultValue = "en") String language) {
        return null;
    }

    @Override
    public ResponseEntity<MovieListDto> getTopRatedMedia() {
        return null;
    }

    @Override
    public ResponseEntity<MovieListDto> getSearchMedia() {
        return null;
    }

    @Override
    public ResponseEntity<MovieListDto> getCreditsMedia() {
        return null;
    }
}
