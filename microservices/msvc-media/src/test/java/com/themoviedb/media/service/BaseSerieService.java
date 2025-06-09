package com.themoviedb.media.service;

import com.themoviedb.media.BaseSerie;
import com.themoviedb.media.client.TvSeriesFeignClient;
import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.dto.serie.SerieDetailDto;
import com.themoviedb.media.dto.serie.SerieDto;
import com.themoviedb.media.dto.serie.SerieListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseSerieService extends BaseSerie {

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

}
