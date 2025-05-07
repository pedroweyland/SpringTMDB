package com.themoviedb.media.controllers;


import com.themoviedb.media.dto.CreditsDto;
import com.themoviedb.media.dto.SerieDetailDto;
import com.themoviedb.media.dto.SerieListDto;
import com.themoviedb.media.service.TvSeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static com.themoviedb.media.controllers.BaseMovieController.getCreditsDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TvSerieControllerTest extends BaseSerieController {

    @Mock
    private TvSeriesService tvSeriesService;

    @InjectMocks
    private TvSeriesController serieController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        serieController = new TvSeriesController(tvSeriesService);
    }

    @Test
    public void getPopularSerieSuccess() {
        SerieListDto serieListDto = getSerieList();

        when(tvSeriesService.getPopularMedia(1, "es")).thenReturn(serieListDto);

        ResponseEntity<SerieListDto> response = serieController.getPopularMedia(1, "es");

        verify(tvSeriesService, times(1)).getPopularMedia(1, "es");
        assertEquals(serieListDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getTopRatedSerieSuccess() {
        SerieListDto serieListDto = getSerieList();

        when(tvSeriesService.getTopRatedMedia(1, "es")).thenReturn(serieListDto);

        ResponseEntity<SerieListDto> response = serieController.getTopRatedMedia(1, "es");

        verify(tvSeriesService, times(1)).getTopRatedMedia(1, "es");
        assertEquals(serieListDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getOnTheAirSerieSuccess() {
        SerieListDto serieListDto = getSerieList();

        when(tvSeriesService.getComingSoonMedia(1, "es")).thenReturn(serieListDto);

        ResponseEntity<SerieListDto> response = serieController.getComingSoonMedia(1, "es");

        verify(tvSeriesService, times(1)).getComingSoonMedia(1, "es");
        assertEquals(serieListDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getSearchSerieSuccess() {
        SerieListDto serieListDto = getSerieList();

        when(tvSeriesService.getSearchMedia(1, "es", "query")).thenReturn(serieListDto);

        ResponseEntity<SerieListDto> response = serieController.getSearchMedia(1, "es", "query");

        verify(tvSeriesService, times(1)).getSearchMedia(1, "es", "query");
        assertEquals(serieListDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getAiringTodaySerieSuccess() {
        SerieListDto serieListDto = getSerieList();

        when(tvSeriesService.getNowPlayingMedia(1, "es")).thenReturn(serieListDto);

        ResponseEntity<SerieListDto> response = serieController.getNowPlayingMedia(1, "es");

        verify(tvSeriesService, times(1)).getNowPlayingMedia(1, "es");
        assertEquals(serieListDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getDetailsSerieSuccess() {
        SerieDetailDto serieDetailDto = getSerieDetailDto();

        when(tvSeriesService.getDetailsSerie(1, "es")).thenReturn(serieDetailDto);

        ResponseEntity<SerieDetailDto> response = serieController.getDetailsSeries(1, "es");

        verify(tvSeriesService, times(1)).getDetailsSerie(1, "es");
        assertEquals(serieDetailDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getCreditsSerieSuccess() {
        CreditsDto creditsDto = getCreditsDto();

        when(tvSeriesService.getCreditsSerie(1, "es")).thenReturn(creditsDto);

        ResponseEntity<CreditsDto> response = serieController.getCreditsSeries(1, "es");

        verify(tvSeriesService, times(1)).getCreditsSerie(1, "es");
        assertEquals(creditsDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
