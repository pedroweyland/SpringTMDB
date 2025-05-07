package com.themoviedb.media.service.serie;

import com.themoviedb.media.dto.SerieDetailDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.MediaNotFoundException;
import com.themoviedb.media.service.BaseSerieService;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetDetailsSerieTest extends BaseSerieService {

    @Test
    public void getDetailsSerieSuccess() {
        int idSerie = 1;
        String language = "es";
        SerieDetailDto serieDetailDto = getSerieDetailDto();

        when(tvSeriesFeignClient.getDetailsSeries(idSerie, language)).thenReturn(serieDetailDto);

        SerieDetailDto result = tvSerieService.getDetailsSerie(idSerie, language);

        assertEquals(serieDetailDto, result);
        verify(tvSeriesFeignClient, times(1)).getDetailsSeries(idSerie, language);
    }

    @Test
    public void getDetailsSerieLanguageException() {
        int idSerie = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> tvSerieService.getDetailsSerie(idSerie, language));
    }

    @Test
    public void getDetailsSerieNotFound() {
        int idSerie = 999; // ID que no existe
        String language = "es";

        // Crear un mock de FeignException con status 404
        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(404);

        when(tvSeriesFeignClient.getDetailsSeries(idSerie, language)).thenThrow(notFoundException);

        MediaNotFoundException exception = assertThrows(MediaNotFoundException.class,
                () -> tvSerieService.getDetailsSerie(idSerie, language));

        assertEquals("Serie not found: " + idSerie, exception.getMessage());
    }

    @Test
    public void getDetailsSerieOtherException() {
        int idSerie = 1;
        String language = "es";

        FeignException serverException = mock(FeignException.class);
        when(serverException.status()).thenReturn(500);
        when(serverException.getMessage()).thenReturn("Server error");

        when(tvSeriesFeignClient.getDetailsSeries(idSerie, language)).thenThrow(serverException);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> tvSerieService.getDetailsSerie(idSerie, language));

        assertTrue(exception.getMessage().contains("Server error"));
    }
}
