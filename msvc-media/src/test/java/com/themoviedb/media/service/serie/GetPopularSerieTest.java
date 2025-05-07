package com.themoviedb.media.service.serie;

import com.themoviedb.media.dto.SerieListDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.BaseSerieService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetPopularSerieTest extends BaseSerieService {

    @Test
    public void testGetPopularSerieSuccess() {
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getPopularSeries(page, language)).thenReturn(serieList);

        SerieListDto response = tvSerieService.getPopularMedia(page, language);

        assertEquals(response, serieList);
        verify(tvSeriesFeignClient, times(1)).getPopularSeries(page, language);
    }

    @Test
    public void testGetPopularSeriePageNotFound() {
        int page = 10;
        String language = "es";

        when(tvSeriesFeignClient.getPopularSeries(page, language)).thenReturn(serieList);

        assertThrows(PageNotFoundException.class, () -> tvSerieService.getPopularMedia(page, language));
    }

    @Test
    public void testGetPopularSerieLanguageNotFound() {
        int page = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> tvSerieService.getPopularMedia(page, language));
    }

    @Test
    public void testGetPopularSerieException() {
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getPopularSeries(page, language)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> tvSerieService.getPopularMedia(page, language));
    }
}
