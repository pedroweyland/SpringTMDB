package com.themoviedb.media.service.people;

import com.themoviedb.media.dto.people.credits.CreditsPeopleDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PersonNotFoundException;
import com.themoviedb.media.service.BasePeopleService;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetCreditsPersonMovieTest extends BasePeopleService {

    @Test
    public void getCreditsPersonMovieTest() {

        CreditsPeopleDto creditsPeopleDto = getCreditsPeopleDto();

        when(peopleFeignClient.getCreditsPersonMovieFetch(1, "es")).thenReturn(creditsPeopleDto);

        CreditsPeopleDto response = peopleService.getCreditsPersonMovie("es", 1);

        assertEquals(creditsPeopleDto, response);
        verify(peopleFeignClient, times(1)).getCreditsPersonMovieFetch(1, "es");
    }

    @Test
    public void getCreditsPersonMovieNotFound() {
        int idPerson = 999;

        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(404);

        when(peopleFeignClient.getCreditsPersonMovieFetch(idPerson, "es")).thenThrow(notFoundException);


        PersonNotFoundException exception = assertThrows(
                PersonNotFoundException.class, () -> peopleService.getCreditsPersonMovie("es", idPerson));

        assertEquals("Person not found: " + idPerson, exception.getMessage());
    }

    @Test
    public void getCreditsPersonMovieLanguageNotFound() {
        int idPerson = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> peopleService.getPersonDetail(language, idPerson));
    }

    @Test
    public void getCreditsPersonMovieOtherException() {
        int idPerson = 1;
        String language = "en";

        FeignException feignException = mock(FeignException.class);
        when(feignException.status()).thenReturn(500);

        when(peopleFeignClient.getCreditsPersonMovieFetch(idPerson, language)).thenThrow(feignException);

        assertThrows(RuntimeException.class, () -> peopleService.getCreditsPersonMovie(language, idPerson));
    }
}
