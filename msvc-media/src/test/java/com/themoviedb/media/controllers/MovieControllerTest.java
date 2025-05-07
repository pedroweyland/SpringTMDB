package com.themoviedb.media.controllers;

import com.themoviedb.media.dto.CreditsDto;
import com.themoviedb.media.dto.MovieDetailDto;
import com.themoviedb.media.dto.MovieListDto;
import com.themoviedb.media.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieControllerTest extends BaseMovieController  {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        movieController = new MovieController(movieService);
    }

    @Test
    public void getPopularMovieSuccess() {
        MovieListDto movieListDto = getMovieList();

        when(movieService.getPopularMedia(1, "es")).thenReturn(movieListDto);

        ResponseEntity<MovieListDto> response = movieController.getPopularMedia(1, "es");

        verify(movieService, times(1)).getPopularMedia(1, "es");
        assertEquals(response.getBody(), movieListDto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getTopRatedMovieSuccess() {
        MovieListDto movieListDto = getMovieList();

        when(movieService.getTopRatedMedia(1, "es")).thenReturn(movieListDto);

        ResponseEntity<MovieListDto> response = movieController.getTopRatedMedia(1, "es");

        verify(movieService, times(1)).getTopRatedMedia(1, "es");
        assertEquals(response.getBody(), movieListDto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getComingSoonMovieSuccess() {
        MovieListDto movieListDto = getMovieList();

        when(movieService.getComingSoonMedia(1, "es")).thenReturn(movieListDto);

        ResponseEntity<MovieListDto> response = movieController.getComingSoonMedia(1, "es");

        verify(movieService, times(1)).getComingSoonMedia(1, "es");
        assertEquals(response.getBody(), movieListDto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getSearchMovieSuccess() {
        MovieListDto movieListDto = getMovieList();

        when(movieService.getSearchMedia(1, "es", "query")).thenReturn(movieListDto);

        ResponseEntity<MovieListDto> response = movieController.getSearchMedia(1, "es", "query");

        verify(movieService, times(1)).getSearchMedia(1, "es", "query");
        assertEquals(response.getBody(), movieListDto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getNowPlayingMovieSuccess() {
        MovieListDto movieListDto = getMovieList();

        when(movieService.getNowPlayingMedia(1, "es")).thenReturn(movieListDto);

        ResponseEntity<MovieListDto> response = movieController.getNowPlayingMedia(1, "es");

        verify(movieService, times(1)).getNowPlayingMedia(1, "es");
        assertEquals(response.getBody(), movieListDto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getDetailsMovieSuccess() {
        MovieDetailDto movieDetailDto = getMovieDetailDto();

        when(movieService.getDetailsMovie(1, "es")).thenReturn(movieDetailDto);

        ResponseEntity<MovieDetailDto> response = movieController.getDetailsMedia(1, "es");

        verify(movieService, times(1)).getDetailsMovie(1, "es");
        assertEquals(response.getBody(), movieDetailDto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getCreditsMovieSuccess() {
        CreditsDto creditsDto = getCreditsDto();

        when(movieService.getCreditsMovie(1, "es")).thenReturn(creditsDto);

        ResponseEntity<CreditsDto> response = movieController.getCreditsMovie(1, "es");

        verify(movieService, times(1)).getCreditsMovie(1, "es");
        assertEquals(response.getBody(), creditsDto);
        assertEquals(200, response.getStatusCodeValue());
    }
}
