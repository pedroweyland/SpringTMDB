package com.themoviedb.media.service;

import com.themoviedb.media.dto.MovieListDto;
import com.themoviedb.media.exception.PageNotFoundException;

import java.util.List;

public interface IMediaService<T> {


   T getPopularMedia(Integer page, String language);

   T getDetailsMedia();

   T getTopRatedMedia();

   T getCreditsMedia();

   T getSearchMedia();
}
