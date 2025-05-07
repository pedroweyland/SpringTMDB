package com.themoviedb.media.service.movie;

import com.themoviedb.media.dto.MovieListDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.BaseMovieService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.*;

public class GetPopularMovieTest extends BaseMovieService {

    @Test
    public void testGetPopularMovieSuccess() {
        int page = 1;
        String language = "es";

        when(movieFeignClient.getPopularMovie(page, language)).thenReturn(movieList);

        MovieListDto response = movieService.getPopularMedia(page, language);

        assertEquals(movieList, response);
        verify(movieFeignClient, times(1)).getPopularMovie(page, language);
    }

    @Test
    public void testGetPopularMoviePageNotFound() {
        int page = 10;
        String language = "es";

        when(movieFeignClient.getPopularMovie(page, language)).thenReturn(movieList);

        assertThrows(PageNotFoundException.class, () -> movieService.getPopularMedia(page, language));
    }

    @Test
    public void testGetPopularMovieLanguageNotFound() {
        int page = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> movieService.getPopularMedia(page, language));
    }

    @Test
    public void testGetPopularMovieException() {
        int page = 1;
        String language = "es";

        when(movieFeignClient.getPopularMovie(page, language)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> movieService.getPopularMedia(page, language));
    }
}