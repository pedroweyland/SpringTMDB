package com.themoviedb.media.service;

import com.themoviedb.media.BaseMovie;
import com.themoviedb.media.client.MovieFeignClient;
import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.dto.movie.MovieDetailDto;
import com.themoviedb.media.dto.movie.MovieDto;
import com.themoviedb.media.dto.movie.MovieListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseMovieService extends BaseMovie {

    @Mock
    protected MovieFeignClient movieFeignClient;

    @InjectMocks
    protected MovieService movieService;

    protected MovieListDto movieList;

    @BeforeEach
    public void baseSetUp() {
        MockitoAnnotations.openMocks(this);
        movieService = new MovieService(movieFeignClient);
        movieList = getMovieList();
    }

}
