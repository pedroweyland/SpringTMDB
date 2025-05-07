package com.themoviedb.media.service.serie;

import com.themoviedb.media.dto.SerieListDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.BaseSerieService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetNowPlayingSerieTest extends BaseSerieService {

    @Test
    public void testGetNowPlayingSerieSuccess() {
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getAiringTodaySeries(page, language)).thenReturn(serieList);

        SerieListDto response = tvSerieService.getNowPlayingMedia(page, language);

        assertEquals(response, serieList);
        verify(tvSeriesFeignClient, times(1)).getAiringTodaySeries(page, language);
    }

    @Test
    public void testGetNowPlayingSeriePageNotFound() {
        int page = 10;
        String language = "es";

        when(tvSeriesFeignClient.getAiringTodaySeries(page, language)).thenReturn(serieList);

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

        when(tvSeriesFeignClient.getAiringTodaySeries(page, language)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> tvSerieService.getNowPlayingMedia(page, language));
    }
}
