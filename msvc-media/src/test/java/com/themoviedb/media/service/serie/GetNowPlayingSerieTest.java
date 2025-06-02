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

public class GetNowPlayingSerieTest extends BaseSerieService {

    @Test
    public void testGetNowPlayingSerieSuccess() {
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getAiringTodaySeriesFetch(page, language)).thenReturn(serieList);

        SerieListDto response = tvSerieService.getNowPlayingMedia(page, language);

        assertEquals(response, serieList);
        verify(tvSeriesFeignClient, times(1)).getAiringTodaySeriesFetch(page, language);
    }

    @Test
    public void testGetNowPlayingSeriePageNotFound() {
        int page = 10;
        String language = "es";

        when(tvSeriesFeignClient.getAiringTodaySeriesFetch(page, language)).thenReturn(serieList);

        assertThrows(PageNotFoundException.class, () -> tvSerieService.getNowPlayingMedia(page, language));
    }

    @Test
    public void testGetNowPlayingSerieLanguageNotFound() {
        int page = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> tvSerieService.getNowPlayingMedia(page, language));
    }

    @Test
    public void testGetNowPlayingSerieException() {
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getAiringTodaySeriesFetch(page, language)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> tvSerieService.getNowPlayingMedia(page, language));
    }

    @Test
    public void testGetNowPlayingPageNotFoundError() {
        int page = 999;
        String language = "en";

        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(400);

        when(tvSeriesFeignClient.getAiringTodaySeriesFetch(page, language)).thenThrow(notFoundException);

        PageNotFoundException exception = assertThrows(
                PageNotFoundException.class, () -> tvSerieService.getNowPlayingMedia(page, language));

        assertEquals("Page not found: " + page, exception.getMessage());
    }
}
