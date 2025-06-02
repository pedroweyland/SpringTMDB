package com.themoviedb.media.service.people;
import com.themoviedb.media.dto.people.credits.CreditsPeopleDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PersonNotFoundException;
import com.themoviedb.media.service.BasePeopleService;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetCreditsPersonSerieTest extends BasePeopleService {

    @Test
    public void getCreditsPersonSerieSuccess() {
        CreditsPeopleDto creditsPeopleDto = getCreditsPeopleDto();

        when(peopleFeignClient.getCreditsPersonTvShowFetch(1, "es")).thenReturn(creditsPeopleDto);

        CreditsPeopleDto response = peopleService.getCreditsPersonTvShow("es", 1);

        assertEquals(creditsPeopleDto, response);
        verify(peopleFeignClient, times(1)).getCreditsPersonTvShowFetch(1, "es");
    }

    @Test
    public void getCreditsPersonSerieNotFound() {
        int idPerson = 999;

        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(404);

        when(peopleFeignClient.getCreditsPersonTvShowFetch(idPerson, "es")).thenThrow(notFoundException);


        PersonNotFoundException exception = assertThrows(
                PersonNotFoundException.class, () -> peopleService.getCreditsPersonTvShow("es", idPerson));

        assertEquals("Person not found: " + idPerson, exception.getMessage());
    }

    @Test
    public void getCreditsPersonSerieLanguageNotFound() {
        int idPerson = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> peopleService.getCreditsPersonTvShow(language, idPerson));
    }

    @Test
    public void getCreditsPersonSerieOtherException() {
        int idPerson = 1;
        String language = "en";

        FeignException feignException = mock(FeignException.class);
        when(feignException.status()).thenReturn(500);

        when(peopleFeignClient.getCreditsPersonTvShowFetch(idPerson, language)).thenThrow(feignException);

        assertThrows(RuntimeException.class, () -> peopleService.getCreditsPersonTvShow(language, idPerson));
    }
}
