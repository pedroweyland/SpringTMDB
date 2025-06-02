package com.themoviedb.media.controllers;

import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.dto.serie.SerieDetailDto;
import com.themoviedb.media.dto.serie.SerieListDto;
import com.themoviedb.media.service.IMediaService;
import com.themoviedb.media.service.TvSeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/media/tv")
@RequiredArgsConstructor
public class TvSeriesController extends BaseMediaController<SerieListDto> {

    private final TvSeriesService tvSeriesService;


    @Override
    protected IMediaService<SerieListDto> getService() {
        return tvSeriesService;
    }

    @Override
    @GetMapping("/on_the_air")
    public ResponseEntity<SerieListDto> getComingSoonMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(tvSeriesService.getComingSoonMedia(page, language));
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
