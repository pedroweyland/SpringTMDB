package com.themoviedb.media.service.serie;

import com.themoviedb.media.dto.serie.SerieListDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.BaseSerieService;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetSearchSerieTest extends BaseSerieService {

    @Test
    public void getSearchSerieSuccess() {
        String query = "batman";
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getSearchSeriesFetch(page, language, query)).thenReturn(serieList);

        SerieListDto response = tvSerieService.getSearchMedia(page, language, query);

        assertEquals(response, serieList);
        verify(tvSeriesFeignClient, times(1)).getSearchSeriesFetch(page, language, query);
    }

    @Test
    public void getSearchSerieQueryNotFound() {
        String query = "batman";
        int page = 10;
        String language = "es";

        SerieListDto serieListResponse = SerieListDto.builder()
                .page(1)
                .results(List.of())
                .totalPages(0)
                .totalResults(0)
                .build();

        when(tvSeriesFeignClient.getSearchSeriesFetch(page, language, query)).thenReturn(serieListResponse);

        assertThrows(IllegalArgumentException.class, () -> tvSerieService.getSearchMedia(page, language, query));
    }

    @Test
    public void getSearchSerieLanguageNotFound() {
        String query = "batman";
        int page = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> tvSerieService.getSearchMedia(page, language, query));
    }

    @Test
    public void getSearchSerieException() {
        String query = "batman";
        int page = 1;
        String language = "es";

        when(tvSeriesFeignClient.getSearchSeriesFetch(page, language, query)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> tvSerieService.getSearchMedia(page, language, query));
    }

    @Test
    public void getSearchSeriePageNotFoundError() {
        int page = 999;
        String language = "en";

        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(400);

        when(tvSeriesFeignClient.getSearchSeriesFetch(page, language, "")).thenThrow(notFoundException);

        PageNotFoundException exception = assertThrows(
                PageNotFoundException.class, () -> tvSerieService.getSearchMedia(page, language, ""));

        assertEquals("Page not found: " + page, exception.getMessage());
    }
}
