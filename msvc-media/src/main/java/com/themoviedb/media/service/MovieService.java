package com.themoviedb.media.service;

import com.themoviedb.media.client.MovieFeignClient;
import com.themoviedb.media.dto.MovieListDto;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.utils.LanguageValidator;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService implements IMediaService<MovieListDto> {

    private final MovieFeignClient movieFeign;


    @Override
    public MovieListDto getPopularMedia(Integer page, String language) {
        try {
            LanguageValidator.validateLanguage(language);

            return movieFeign.getPopularMovie(page, language.toLowerCase());
        } catch (FeignException e) {
            if (e.status() == 400) {
                throw new PageNotFoundException("Page not found: " + page);
            }
            throw new RuntimeException("Something went wrong while fetching popular movies: " + e.getMessage());
        }
    }

    @Override
    public MovieListDto getTopRatedMedia() {
        return null;
    }

    @Override
    public MovieListDto getDetailsMedia() {
        return null;
    }

    @Override
    public MovieListDto getCreditsMedia() {
        return null;
    }

    @Override
    public MovieListDto getSearchMedia() {
        return null;
    }
}
