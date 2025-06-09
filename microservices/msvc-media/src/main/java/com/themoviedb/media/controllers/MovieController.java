package com.themoviedb.media.controllers;

import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.dto.movie.MovieDetailDto;
import com.themoviedb.media.dto.movie.MovieListDto;
import com.themoviedb.media.service.IMediaService;
import com.themoviedb.media.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/media/movie")
@RequiredArgsConstructor
public class MovieController extends BaseMediaController<MovieListDto> {

    private final MovieService movieService;

    @Override
    protected IMediaService<MovieListDto> getService() {
        return movieService;
    }

    @GetMapping("/details/{idMovie}")
    public ResponseEntity<MovieDetailDto> getDetailsMedia(
            @PathVariable Integer idMovie,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(movieService.getDetailsMovie(idMovie, language));
    }

    @GetMapping("/{idMovie}/credits")
    public ResponseEntity<CreditsDto> getCreditsMovie(
            @PathVariable Integer idMovie,
            @RequestParam(defaultValue = "en") String language
    ) {
        return ResponseEntity.ok(movieService.getCreditsMovie(idMovie, language));
    }

}

