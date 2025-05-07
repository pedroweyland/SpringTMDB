package com.themoviedb.media.service.movie;

import com.themoviedb.media.dto.MovieListDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.service.BaseMovieService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetSearchMovieTest extends BaseMovieService {

    @Test
    public void testGetSearchMovieSuccess() {
        String query = "query";
        int page = 1;
        String language = "es";

        when(movieFeignClient.getSearchMovie(page, language, query)).thenReturn(movieList);

        MovieListDto response = movieService.getSearchMedia(page, language, query);

        assertEquals(movieList, response);
        verify(movieFeignClient, times(1)).getSearchMovie(page, language, query);
    }

    @Test
    public void testGetSearchMovieQueryNotFound() {
        String query = "query";
        int page = 1;
        String language = "es";

        MovieListDto movieListResponse = MovieListDto.builder()
                .page(1)
                .results(List.of())
                .totalPages(0)
                .totalResults(0)
                .build();

        when(movieFeignClient.getSearchMovie(page, language, query)).thenReturn(movieListResponse);

        assertThrows(IllegalArgumentException.class, () -> movieService.getSearchMedia(page, language, query));
    }

    @Test
    public void testGetSearchMovieLanguageNotFound() {
        String query = "query";
        int page = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> movieService.getSearchMedia(page, language, query));
    }

    @Test
    public void testGetSearchMovieException() {
        String query = "query";
        int page = 1;
        String language = "es";

        when(movieFeignClient.getSearchMovie(page, language, query)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> movieService.getSearchMedia(page, language, query));
    }
}
