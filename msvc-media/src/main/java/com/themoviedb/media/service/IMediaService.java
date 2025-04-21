package com.themoviedb.media.service;

public interface IMediaService<T> {

   T getPopularMedia(Integer page, String language);

   T getTopRatedMedia(Integer page, String language);

   T getComingSoonMedia(Integer page, String language);

   T getSearchMedia(Integer page, String language, String query);

   T getNowPlayingMedia(Integer page, String language);

}
