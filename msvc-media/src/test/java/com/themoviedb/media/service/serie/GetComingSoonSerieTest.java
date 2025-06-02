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

public class GetComingSoonSerieTest extends BaseSerieService {

    @Test
    public void getComingSoonSerieSuccess() {
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getOnTheAirSeriesFetch(page, language)).thenReturn(serieList);

        SerieListDto result = tvSerieService.getComingSoonMedia(page, language);

        assertEquals(serieList, result);
    }

    @Test
    public void getComingSoonSeriePageNotFound() {
        int page = 10;
        String language = "es";

        when(tvSeriesFeignClient.getOnTheAirSeriesFetch(page, language)).thenReturn(serieList);

        assertThrows(PageNotFoundException.class, () -> tvSerieService.getComingSoonMedia(page, language));
    }

    @Test
    public void getComingSoonSerieLanguageNotFound() {
        int page = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> tvSerieService.getComingSoonMedia(page, language));
    }

    @Test
    public void getComingSoonSerieException() {
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getOnTheAirSeriesFetch(page, language)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> tvSerieService.getComingSoonMedia(page, language));
    }

    @Test
    public void getComingSoonSeriePageNotFoundError() {
        int page = 999;
        String language = "en";

        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(400);

        when(tvSeriesFeignClient.getOnTheAirSeriesFetch(page, language)).thenThrow(notFoundException);

        PageNotFoundException exception = assertThrows(
                PageNotFoundException.class, () -> tvSerieService.getComingSoonMedia(page, language));

        assertEquals("Page not found: " + page, exception.getMessage());
    }
}
