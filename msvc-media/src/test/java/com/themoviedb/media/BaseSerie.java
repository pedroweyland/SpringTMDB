package com.themoviedb.media;

import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.dto.serie.SerieDetailDto;
import com.themoviedb.media.dto.serie.SerieDto;
import com.themoviedb.media.dto.serie.SerieListDto;

import java.time.LocalDate;
import java.util.List;

public abstract class BaseSerie {

    protected static SerieListDto getSerieList() {
        return SerieListDto.builder()
                .page(1)
                .results(List.of(getSerieDto()))
                .totalPages(1)
                .totalResults(0)
                .build();
    }

    protected static SerieDto getSerieDto() {
        return SerieDto.builder()
                .id(1)
                .name("test serie")
                .overview("test serie overview")
                .popularity(10.0)
                .backdropPath("test backdropPath")
                .firstAirDate(LocalDate.now())
                .originalLanguage("test language")
                .originalName("test serie")
                .posterPath("/123456789.jpg")
                .voteAverage(10.0)
                .voteCount(2)
                .build();
    }

    protected static SerieDetailDto getSerieDetailDto() {
        return SerieDetailDto.builder()
                .id(1)
                .adult(false)
                .name("test serie")
                .homepage("test homepage")
                .overview("test serie overview")
                .status("test status")
                .tagline("test tagline")
                .type("test type")
                .popularity(10.0)
                .lastAirDate(LocalDate.now())
                .build();
    }

    protected static CreditsDto getSerieCreditsDto() {
        return CreditsDto.builder()
                .cast(List.of())
                .crew(List.of())
                .id(1)
                .build();
    }
}
