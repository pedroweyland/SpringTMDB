package com.themoviedb.media.controllers;

import com.themoviedb.media.dto.CreditsDto;
import com.themoviedb.media.dto.SerieDetailDto;
import com.themoviedb.media.dto.SerieListDto;
import com.themoviedb.media.service.TvSeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/top_rated")
    public ResponseEntity<SerieListDto> getTopRatedMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(tvSeriesService.getTopRatedMedia(page, language));
    }

    @Override
    @GetMapping("/on_the_air")
    public ResponseEntity<SerieListDto> getComingSoonMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(tvSeriesService.getComingSoonMedia(page, language));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<SerieListDto> getSearchMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language,
            @RequestParam(defaultValue = "") String query) {
        return ResponseEntity.ok(tvSeriesService.getSearchMedia(page, language, query));
    }

    @Override
    @GetMapping("/airing_today")
    public ResponseEntity<SerieListDto> getNowPlayingMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(tvSeriesService.getNowPlayingMedia(page, language));
    }

    @GetMapping("/details/{idSerie}")
    public ResponseEntity<SerieDetailDto> getDetailsSeries(
            @PathVariable Integer idSerie,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(tvSeriesService.getDetailsSerie(idSerie, language));
    }

    @GetMapping("/{idSerie}/credits")
    public ResponseEntity<CreditsDto> getCreditsSeries(
            @PathVariable Integer idSerie,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(tvSeriesService.getCreditsSerie(idSerie, language));
    }

}
