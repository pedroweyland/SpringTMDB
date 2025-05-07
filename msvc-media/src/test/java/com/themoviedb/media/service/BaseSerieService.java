package com.themoviedb.media.service;

import com.themoviedb.media.client.TvSeriesFeignClient;
import com.themoviedb.media.dto.CreditsDto;
import com.themoviedb.media.dto.SerieDetailDto;
import com.themoviedb.media.dto.SerieDto;
import com.themoviedb.media.dto.SerieListDto;
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
public abstract class BaseSerieService {

    @Mock
    protected TvSeriesFeignClient tvSeriesFeignClient;

    @InjectMocks
    protected TvSeriesService tvSerieService;

    protected SerieListDto serieList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tvSerieService = new TvSeriesService(tvSeriesFeignClient);
        serieList = getSerieList();
    }

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
                .firstAirDate("test firstAirDate")
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
                .lastAirDate("test lastAirDate")
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
