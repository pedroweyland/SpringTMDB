package com.themoviedb.media.service;

import com.themoviedb.media.client.MovieFeignClient;
import com.themoviedb.media.dto.CreditsDto;
import com.themoviedb.media.dto.MovieDetailDto;
import com.themoviedb.media.dto.MovieListDto;
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
                    MovieListDto movieList = movieFeign.getPopularMovie(page, language.toLowerCase());
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
                    MovieListDto movieList = movieFeign.getTopRatedMovie(page, language.toLowerCase());
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
                    MovieListDto movieList = movieFeign.getComingSoonMovie(page, language.toLowerCase());
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
                    MovieListDto movieList = movieFeign.getSearchMovie(page, language.toLowerCase(), query);
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
                    MovieListDto movieList = movieFeign.getNowPlayingMovie(page, language.toLowerCase());
                    validatePageContent(movieList.getTotalPages(), page);
                    return movieList;
                },
                page
        );
    }

    public MovieDetailDto getDetailsMovie(Integer idMovie, String language) {
        try {
            validateLanguage(language);

            return movieFeign.getDetailsMovie(idMovie, language.toLowerCase());
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

            return movieFeign.getCreditsMovie(idMovie, language.toLowerCase());
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new MediaNotFoundException("Movie not found: " + idMovie);
            }
            throw new RuntimeException("Something went wrong while fetching popular movies: " + e.getMessage());
        }
    }
}
