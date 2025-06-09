package com.themoviedb.media.controllers;

import org.springframework.http.ResponseEntity;

public interface IMediaController<T> {

    ResponseEntity<T> getPopularMedia(Integer page, String language);

    ResponseEntity<T> getTopRatedMedia(Integer page, String language);

    ResponseEntity<T> getComingSoonMedia(Integer page, String language);

    ResponseEntity<T> getSearchMedia(Integer page, String language, String query);

    ResponseEntity<T> getNowPlayingMedia(Integer page, String language);

}
