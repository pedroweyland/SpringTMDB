package com.themoviedb.media.service.movie;

import com.themoviedb.media.dto.movie.MovieListDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.BaseMovieService;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetComingSoonMovieTest extends BaseMovieService {

    @Test
    public void testGetComingSoonMovieSuccess() {
        int page = 1;
        String language = "es";

        when(movieFeignClient.getComingSoonMovieFetch(page, language)).thenReturn(movieList);

        MovieListDto response = movieService.getComingSoonMedia(page, language);

        assertEquals(movieList, response);
        verify(movieFeignClient, times(1)).getComingSoonMovieFetch(page, language);
    }

    @Test
    public void testGetComingSoonMoviePageNotFound() {
        int page = 10;
        String language = "es";

        when(movieFeignClient.getComingSoonMovieFetch(page, language)).thenReturn(movieList);

        assertThrows(PageNotFoundException.class, () -> movieService.getComingSoonMedia(page, language));
    }

    @Test
    public void testGetComingSoonMovieLanguageNotFound() {
        int page = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> movieService.getComingSoonMedia(page, language));
    }

    @Test
    public void testGetComingSoonMovieException() {
        int page = 1;
        String language = "es";

        when(movieFeignClient.getComingSoonMovieFetch(page, language)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> movieService.getComingSoonMedia(page, language));
    }

    @Test
    public void testGetComingSoonMoviePageNotFoundError() {
        int page = 999;
        String language = "en";

        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(400);

        when(movieFeignClient.getComingSoonMovieFetch(page, language)).thenThrow(notFoundException);

        PageNotFoundException exception = assertThrows(
                PageNotFoundException.class, () -> movieService.getComingSoonMedia(page, language));

        assertEquals("Page not found: " + page, exception.getMessage());
    }
}
