package com.themoviedb.media.controllers;

import com.themoviedb.media.service.IMediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class BaseMediaController<T> implements IMediaController<T> {

    protected abstract IMediaService<T> getService();

    @Override
    @GetMapping("/popular")
    public ResponseEntity<T> getPopularMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(getService().getPopularMedia(page, language));
    }

    @Override
    @GetMapping("/top_rated")
    public ResponseEntity<T> getTopRatedMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(getService().getTopRatedMedia(page, language));
    }

    @Override
    @GetMapping("/upcoming")
    public ResponseEntity<T> getComingSoonMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(getService().getComingSoonMedia(page, language));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<T> getSearchMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language,
            @RequestParam(defaultValue = "") String query) {
        return ResponseEntity.ok(getService().getSearchMedia(page, language, query));
    }

    @Override
    @GetMapping("/now_playing")
    public ResponseEntity<T> getNowPlayingMedia(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language) {
        return ResponseEntity.ok(getService().getNowPlayingMedia(page, language));
    }

}
