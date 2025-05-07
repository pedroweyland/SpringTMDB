package com.themoviedb.media.service.serie;

import com.themoviedb.media.dto.SerieListDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.BaseSerieService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetTopRatedSerieTest extends BaseSerieService {

    @Test
    public void getTopRatedSerieSuccess() {
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getTopRatedSeries(page, language)).thenReturn(serieList);

        SerieListDto response = tvSerieService.getTopRatedMedia(page, language);

        assertEquals(response, serieList);
        verify(tvSeriesFeignClient, times(1)).getTopRatedSeries(page, language);
    }

    @Test
    public void getTopRatedSeriePageNotFound() {
        int page = 10;
        String language = "es";

        when(tvSeriesFeignClient.getTopRatedSeries(page, language)).thenReturn(serieList);

        assertThrows(PageNotFoundException.class, () -> tvSerieService.getTopRatedMedia(page, language));
    }

    @Test
    public void getTopRatedSerieLanguageNotFound() {
        int page = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> tvSerieService.getTopRatedMedia(page, language));
    }

    @Test
    public void getTopRatedSerieException() {
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getTopRatedSeries(page, language)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> tvSerieService.getTopRatedMedia(page, language));
    }
}
