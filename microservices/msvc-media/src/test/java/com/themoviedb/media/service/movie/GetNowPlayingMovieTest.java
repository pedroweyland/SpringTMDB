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

public class GetNowPlayingMovieTest extends BaseMovieService {

    @Test
    public void testGetNowPlayingMovieSuccess() {
        int page = 1;
        String language = "es";

        when(movieFeignClient.getNowPlayingMovieFetch(page, language)).thenReturn(movieList);

        MovieListDto movieListDto = movieService.getNowPlayingMedia(page, language);

        assertEquals(movieList, movieListDto);
        verify(movieFeignClient, times(1)).getNowPlayingMovieFetch(page, language);
    }

    @Test
    public void testGetNowPlayingMoviePageNotFound() {
        int page = 10;
        String language = "es";

        when(movieFeignClient.getNowPlayingMovieFetch(page, language)).thenReturn(movieList);

        assertThrows(PageNotFoundException.class, () -> movieService.getNowPlayingMedia(page, language));
    }

    @Test
    public void testGetNowPlayingMovieLanguageNotFound() {
        int page = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> movieService.getNowPlayingMedia(page, language));
    }

    @Test
    public void testGetNowPlayingMovieException() {
        int page = 1;
        String language = "es";

        when(movieFeignClient.getNowPlayingMovieFetch(page, language)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> movieService.getNowPlayingMedia(page, language));
    }

    @Test
    public void testGetNowPlayingMoviePageNotFoundError() {
        int page = 999;
        String language = "es";

        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(400);

        when(movieFeignClient.getNowPlayingMovieFetch(page, language)).thenThrow(notFoundException);

        PageNotFoundException exception = assertThrows(
                PageNotFoundException.class, () -> movieService.getNowPlayingMedia(page, language));

        assertEquals("Page not found: " + page, exception.getMessage());
    }

}
