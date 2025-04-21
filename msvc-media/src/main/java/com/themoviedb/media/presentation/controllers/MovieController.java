package com.themoviedb.media.presentation.controllers;

import com.themoviedb.media.dto.CreditsDto;
import com.themoviedb.media.dto.MovieDetailDto;
import com.themoviedb.media.dto.MovieListDto;
import com.themoviedb.media.service.MovieService;
import feign.Response;
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

    @GetMapping("/details/{idMovie}")
    public ResponseEntity<MovieDetailDto> getDetailsMedia(
            @PathVariable Integer idMovie,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(movieService.getDetailsMovie(idMovie, language));
    }

    @Override
    @GetMapping("/top_rated")
    public ResponseEntity<MovieListDto> getTopRatedMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(movieService.getTopRatedMedia(page, language));
    }

    @Override
    @GetMapping("/upcoming")
    public ResponseEntity<MovieListDto> getComingSoonMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language
    ) {
        return ResponseEntity.ok(movieService.getComingSoonMedia(page, language));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<MovieListDto> getSearchMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language,
            @RequestParam(defaultValue = "") String query
    ) {
        return ResponseEntity.ok(movieService.getSearchMedia(page, language, query));
    }

    @Override
    @GetMapping("/now_playing")
    public ResponseEntity<MovieListDto> getNowPlayingMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language
    ) {
        return ResponseEntity.ok(movieService.getNowPlayingMedia(page, language));
    }

    @GetMapping("/{idMovie}/credits")
    public ResponseEntity<CreditsDto> getCreditsMovie(
            @PathVariable Integer idMovie,
            @RequestParam(defaultValue = "en") String language
    ) {
        return ResponseEntity.ok(movieService.getCreditsMovie(idMovie, language));
    }

}

