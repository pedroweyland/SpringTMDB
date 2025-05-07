package com.themoviedb.media.service;

import com.themoviedb.media.client.MovieFeignClient;
import com.themoviedb.media.dto.CreditsDto;
import com.themoviedb.media.dto.MovieDetailDto;
import com.themoviedb.media.dto.MovieDto;
import com.themoviedb.media.dto.MovieListDto;
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
public abstract class BaseMovieService {
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

    protected static MovieListDto getMovieList() {

        return MovieListDto.builder()
                .page(1)
                .results(List.of(getMovieDto()))
                .totalPages(1)
                .totalResults(0)
                .build();
    }

    protected static MovieDto getMovieDto() {

        return MovieDto.builder()
                .id(1)
                .adult(false)
                .overview("test movie overview")
                .popularity(10.0)
                .title("test movie")
                .video(false)
                .backdropPath("")
                .genreIds(null)
                .originalLanguage("")
                .originalTitle("")
                .posterPath("/123456789.jpg")
                .releaseDate(null)
                .voteAverage(10.0)
                .voteCount(2)
                .build();
    }

    protected static MovieDetailDto getMovieDetailDto() {
        return MovieDetailDto.builder()
                .adult(false)
                .backdropPath("")
                .budget(0)
                .genres(null)
                .homepage("")
                .id(1)
                .imdbId("")
                .originalLanguage("")
                .originalTitle("")
                .overview("test movie overview")
                .status("")
                .tagline("")
                .title("test movie")
                .video(false)
                .build();
    }

    protected static CreditsDto getCreditsDto() {
        return CreditsDto.builder()
                .cast(List.of())
                .crew(List.of())
                .id(1)
                .build();
    }
}
