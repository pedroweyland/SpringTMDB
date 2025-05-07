package com.themoviedb.media.service.movie;

import com.themoviedb.media.dto.MovieListDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.BaseMovieService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetTopRatedMovieTest extends BaseMovieService {

    @Test
    public void testGetTopRatedMovieSuccess() {
        int page = 1;
        String language = "es";

        when(movieFeignClient.getTopRatedMovie(page, language)).thenReturn(movieList);

        MovieListDto response = movieService.getTopRatedMedia(page, language);

        assertEquals(movieList, response);
        verify(movieFeignClient, times(1)).getTopRatedMovie(page, language);
    }

    @Test
    public void testGetTopRatedMoviePageNotFound() {
        int page = 10;
        String language = "es";

        when(movieFeignClient.getTopRatedMovie(page, language)).thenReturn(movieList);

        assertThrows(PageNotFoundException.class, () -> movieService.getTopRatedMedia(page, language));
    }

    @Test
    public void testGetTopRatedMovieLanguageNotFound() {
        int page = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> movieService.getTopRatedMedia(page, language));
    }

    @Test
    public void testGetTopRatedMovieException() {
        int page = 1;
        String language = "es";

        when(movieFeignClient.getTopRatedMovie(page, language)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> movieService.getTopRatedMedia(page, language));
    }
}
