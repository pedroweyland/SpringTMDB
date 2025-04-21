package com.themoviedb.media.presentation.controllers;

import com.themoviedb.media.dto.SerieListDto;
import com.themoviedb.media.service.TvSeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/media/tv")
@RequiredArgsConstructor
public class TvSeriesController implements IMediaController<SerieListDto>{

    private final TvSeriesService tvSeriesService;

    @Override
    @GetMapping("/popular")
    public ResponseEntity<SerieListDto> getPopularMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(tvSeriesService.getPopularMedia(page, language));
    }

    @Override
    public ResponseEntity<SerieListDto> getTopRatedMedia(Integer page, String language) {
        return null;
    }

    @Override
    public ResponseEntity<SerieListDto> getComingSoonMedia(Integer page, String language) {
        return null;
    }

    @Override
    public ResponseEntity<SerieListDto> getSearchMedia(Integer page, String language, String query) {
        return null;
    }

    @Override
    public ResponseEntity<SerieListDto> getNowPlayingMedia(Integer page, String language) {
        return null;
    }
}
