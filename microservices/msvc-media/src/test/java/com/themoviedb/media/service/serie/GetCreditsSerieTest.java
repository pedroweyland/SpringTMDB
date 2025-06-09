package com.themoviedb.media.service.serie;

import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.MediaNotFoundException;
import com.themoviedb.media.service.BaseSerieService;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetCreditsSerieTest extends BaseSerieService {

    @Test
    public void getCreditsSerieSuccess() {
        int idSerie = 1;
        String language = "es";
        CreditsDto creditsDto = getSerieCreditsDto();

        when(tvSeriesFeignClient.getCreditsSeriesFetch(idSerie, language)).thenReturn(creditsDto);

        CreditsDto response = tvSeriesFeignClient.getCreditsSeriesFetch(idSerie, language);

        assertEquals(creditsDto, response);
        verify(tvSeriesFeignClient, times(1)).getCreditsSeriesFetch(idSerie, language);
    }

    @Test
    public void getCreditsLanguageNotFound() {
        int idSerie = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> tvSerieService.getCreditsSerie(idSerie, language));
    }

    @Test
    public void getCreditsSerieNotFound() {
        int idSerie = 999; // ID que no existe
        String language = "es";

        // Crear un mock de FeignException con status 404
        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(404);

        when(tvSeriesFeignClient.getCreditsSeriesFetch(idSerie, language)).thenThrow(notFoundException);

        MediaNotFoundException exception = assertThrows(MediaNotFoundException.class,
                () -> tvSerieService.getCreditsSerie(idSerie, language));

        assertEquals("Serie not found: " + idSerie, exception.getMessage());
    }

    @Test
    public void getCreditsSerieOtherException() {
        int idSerie = 1;
        String language = "es";

        FeignException serverException = mock(FeignException.class);
        when(serverException.status()).thenReturn(500);
        when(serverException.getMessage()).thenReturn("Server error");

        when(tvSeriesFeignClient.getCreditsSeriesFetch(idSerie, language)).thenThrow(serverException);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> tvSerieService.getCreditsSerie(idSerie, language));

        assertTrue(exception.getMessage().contains("Server error"));
    }
}
