package com.themoviedb.media.service.movie;

import com.themoviedb.media.dto.movie.MovieDetailDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.MediaNotFoundException;
import com.themoviedb.media.service.BaseMovieService;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetDetailsMovieTest extends BaseMovieService {

    @Test
    public void testGetDetailsMovieSuccess() {
        int idMovie = 1;
        String language = "es";
        MovieDetailDto movieDetailDto = getMovieDetailDto();

        when(movieFeignClient.getDetailsMovieFetch(idMovie, language)).thenReturn(movieDetailDto);

        MovieDetailDto response = movieService.getDetailsMovie(idMovie, language);

        assertEquals(movieDetailDto, response);
        verify(movieFeignClient, times(1)).getDetailsMovieFetch(idMovie, language);
    }

    @Test
    public void testGetDetailsMovieLanguageNotFound() {
        int idMovie = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> movieService.getDetailsMovie(idMovie, language));
    }

    @Test
    public void testGetDetailsMovieNotFound() {
        int idMovie = 999; // ID que no existe
        String language = "es";

        // Crear un mock de FeignException con status 404
        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(404);

        when(movieFeignClient.getDetailsMovieFetch(idMovie, language)).thenThrow(notFoundException);

        MediaNotFoundException exception = assertThrows(MediaNotFoundException.class,
                () -> movieService.getDetailsMovie(idMovie, language));

        assertEquals("Movie not found: " + idMovie, exception.getMessage());
    }

    @Test
    public void testGetDetailsOtherException() {
        int idMovie = 1;
        String language = "es";

        FeignException serverException = mock(FeignException.class);
        when(serverException.status()).thenReturn(500);
        when(serverException.getMessage()).thenReturn("Server error");

        when(movieFeignClient.getDetailsMovieFetch(idMovie, language)).thenThrow(serverException);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> movieService.getDetailsMovie(idMovie, language));

        assertTrue(exception.getMessage().contains("Server error"));
    }
}
