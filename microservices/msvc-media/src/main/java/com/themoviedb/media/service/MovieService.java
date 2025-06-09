package com.themoviedb.media.service;

import com.themoviedb.media.client.MovieFeignClient;
import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.dto.movie.MovieDetailDto;
import com.themoviedb.media.dto.movie.MovieListDto;
import com.themoviedb.media.exception.MediaNotFoundException;
import com.themoviedb.media.service.utils.AbstractMediaService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService extends AbstractMediaService<MovieListDto> implements IMediaService<MovieListDto> {

    private final MovieFeignClient movieFeign;

    @Override
    public MovieListDto getPopularMedia(Integer page, String language) {
        validateLanguage(language);

        return executeWithHandling(
                () -> {
                    MovieListDto movieList = movieFeign.getPopularMovieFetch(page, language.toLowerCase());
                    validatePageContent(movieList.getTotalPages(), page);
                    return movieList;
                },
                page
        );
    }

    @Override
    public MovieListDto getTopRatedMedia(Integer page, String language) {
        validateLanguage(language);

        return executeWithHandling(
                () -> {
                    MovieListDto movieList = movieFeign.getTopRatedMovieFetch(page, language.toLowerCase());
                    validatePageContent(movieList.getTotalPages(), page);
                    return movieList;
                },
                page
        );
    }

    @Override
    public MovieListDto getComingSoonMedia(Integer page, String language) {
        validateLanguage(language);

        return executeWithHandling(
                () -> {
                    MovieListDto movieList = movieFeign.getComingSoonMovieFetch(page, language.toLowerCase());
                    validatePageContent(movieList.getTotalPages(), page);
                    return movieList;
                },
                page
        ) ;
    }

    @Override
    public MovieListDto getSearchMedia(Integer page, String language, String query) {
        validateLanguage(language);

        return executeWithHandling(
                () -> {
                    MovieListDto movieList = movieFeign.getSearchMovieFetch(page, language.toLowerCase(), query);
                    validateSearchQuery(movieList.getResults().isEmpty(), query);
                    return movieList;
                },
                page
        );
    }

    @Override
    public MovieListDto getNowPlayingMedia(Integer page, String language) {
        validateLanguage(language);

        return executeWithHandling(
                () -> {
                    MovieListDto movieList = movieFeign.getNowPlayingMovieFetch(page, language.toLowerCase());
                    validatePageContent(movieList.getTotalPages(), page);
                    return movieList;
                },
                page
        );
    }

    public MovieDetailDto getDetailsMovie(Integer idMovie, String language) {
        try {
            validateLanguage(language);

            return movieFeign.getDetailsMovieFetch(idMovie, language.toLowerCase());
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new MediaNotFoundException("Movie not found: " + idMovie);
            }
            throw new RuntimeException("Something went wrong while fetching popular movies: " + e.getMessage());
        }
    }

    public CreditsDto getCreditsMovie(Integer idMovie, String language) {
        try {
            validateLanguage(language);

            return movieFeign.getCreditsMovieFetch(idMovie, language.toLowerCase());
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new MediaNotFoundException("Movie not found: " + idMovie);
            }
            throw new RuntimeException("Something went wrong while fetching popular movies: " + e.getMessage());
        }
    }
}
