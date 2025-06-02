package com.themoviedb.media.service.serie;

import com.themoviedb.media.dto.serie.SerieListDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.BaseSerieService;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetPopularSerieTest extends BaseSerieService {

    @Test
    public void testGetPopularSerieSuccess() {
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getPopularSeriesFetch(page, language)).thenReturn(serieList);

        SerieListDto response = tvSerieService.getPopularMedia(page, language);

        assertEquals(response, serieList);
        verify(tvSeriesFeignClient, times(1)).getPopularSeriesFetch(page, language);
    }

    @Test
    public void testGetPopularSeriePageNotFound() {
        int page = 10;
        String language = "es";

        when(tvSeriesFeignClient.getPopularSeriesFetch(page, language)).thenReturn(serieList);

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

        when(tvSeriesFeignClient.getPopularSeriesFetch(page, language)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> tvSerieService.getPopularMedia(page, language));
    }

    @Test
    public void testGetPopularSeriePageNotFoundError() {
        int page = 999;
        String language = "en";

        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(400);

        when(tvSeriesFeignClient.getPopularSeriesFetch(page, language)).thenThrow(notFoundException);

        PageNotFoundException exception = assertThrows(
                PageNotFoundException.class, () -> tvSerieService.getPopularMedia(page, language));

        assertEquals("Page not found: " + page, exception.getMessage());
    }
}
