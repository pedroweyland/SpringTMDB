package com.themoviedb.media.presentation.controllers;

import org.springframework.http.ResponseEntity;

public interface IMediaController<T> {

    ResponseEntity<T> getPopularMedia(Integer page, String language);

    ResponseEntity<T> getDetailsMedia(Integer idMedia, String language);

    ResponseEntity<T> getTopRatedMedia();

    ResponseEntity<T> getSearchMedia();

    ResponseEntity<T> getCreditsMedia();
}
