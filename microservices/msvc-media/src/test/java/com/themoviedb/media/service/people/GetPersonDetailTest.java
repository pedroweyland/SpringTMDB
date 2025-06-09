package com.themoviedb.media.service.people;

import com.themoviedb.media.dto.people.PersonDetailDto;
import com.themoviedb.media.dto.people.PersonDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PersonNotFoundException;
import com.themoviedb.media.service.BasePeopleService;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetPersonDetailTest extends BasePeopleService {

    @Test
    public void testGetPersonDetailSuccess() {
        PersonDetailDto personDetailDto = getPersonDetailDto();

        when(peopleFeignClient.getPersonDetailFetch(1, "en")).thenReturn(personDetailDto);

        PersonDetailDto response = peopleService.getPersonDetail("en", 1);

        assertEquals(personDetailDto, response);
        verify(peopleFeignClient, times(1)).getPersonDetailFetch(1, "en");
    }

    @Test
    public void testGetPersonDetailPersonNotFoundException() {

        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(404);

        when(peopleFeignClient.getPersonDetailFetch(1, "en")).thenThrow(notFoundException);

        assertThrows(PersonNotFoundException.class, () -> peopleService.getPersonDetail("en", 1));
    }

    @Test
    public void testGetPersonDetailLanguageNotFoundException() {
        int idPerson = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> peopleService.getPersonDetail(language, idPerson));
    }

    @Test
    public void testGetPersonDetailOtherException() {
        int idPerson = 1;
        String language = "en";

        FeignException feignException = mock(FeignException.class);
        when(feignException.status()).thenReturn(500);

        when(peopleFeignClient.getPersonDetailFetch(idPerson, language)).thenThrow(feignException);

        assertThrows(RuntimeException.class, () -> peopleService.getPersonDetail(language, idPerson));
    }
}
