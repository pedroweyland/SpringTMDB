package com.themoviedb.media.service.movie;

import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.MediaNotFoundException;
import com.themoviedb.media.service.BaseMovieService;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetCreditsMovieTest extends BaseMovieService {

    @Test
    public void testGetCredits() {
        int idMovie = 1;
        String language = "es";
        CreditsDto creditsDto = getCreditsDto();

        when(movieFeignClient.getCreditsMovieFetch(idMovie, language)).thenReturn(creditsDto);

        CreditsDto response = movieService.getCreditsMovie(idMovie, language);

        assertEquals(creditsDto, response);
        verify(movieFeignClient, times(1)).getCreditsMovieFetch(idMovie, language);
    }

    @Test
    public void testGetCreditsLanguageNotFound() {
        int idMovie = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> movieService.getCreditsMovie(idMovie, language));
    }

    @Test
    public void testGetCreditsMovieNotFound() {
        int idMovie = 999; // ID que no existe
        String language = "es";

        // Crear un mock de FeignException con status 404
        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(404);

        when(movieFeignClient.getCreditsMovieFetch(idMovie, language)).thenThrow(notFoundException);

        MediaNotFoundException exception = assertThrows(MediaNotFoundException.class,
                () -> movieService.getCreditsMovie(idMovie, language));

        assertEquals("Movie not found: " + idMovie, exception.getMessage());
    }

    @Test
    public void testGetCreditsOtherException() {
        int idMovie = 1;
        String language = "es";

        FeignException serverException = mock(FeignException.class);
        when(serverException.status()).thenReturn(500);
        when(serverException.getMessage()).thenReturn("Server error");

        when(movieFeignClient.getCreditsMovieFetch(idMovie, language)).thenThrow(serverException);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> movieService.getCreditsMovie(idMovie, language));

        assertTrue(exception.getMessage().contains("Server error"));
    }
}
