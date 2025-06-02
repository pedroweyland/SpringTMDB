package com.themoviedb.media;

import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.dto.movie.MovieDetailDto;
import com.themoviedb.media.dto.movie.MovieDto;
import com.themoviedb.media.dto.movie.MovieListDto;

import java.util.List;

public abstract class BaseMovie {

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
