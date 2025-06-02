package com.themoviedb.media.service.people;

import com.themoviedb.media.dto.people.PeopleListDto;
import com.themoviedb.media.exception.LanguagueNotFoundException;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.BasePeopleService;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetPopularPeopleTest extends BasePeopleService {

    @Test
    public void testPopularPeopleSuccess() {
        int page = 1;
        String language = "en";

        when(peopleFeignClient.getPopularPeopleFetch(page, language)).thenReturn(getPeopleListDto());

        PeopleListDto response = peopleService.getPopularPeople(page, language);

        assertEquals(getPeopleListDto(), response);
        verify(peopleFeignClient, times(1)).getPopularPeopleFetch(page, language);
    }

    @Test
    public void testPopularPeoplePageNotFound() {
        int page = 999;
        String language = "en";

        when(peopleFeignClient.getPopularPeopleFetch(page, language)).thenReturn(getPeopleListDto());

        assertThrows(PageNotFoundException.class, () -> peopleService.getPopularPeople(page, language));
    }

    @Test
    public void testPopularPeoplePageNotFoundError() {
        int page = 999;
        String language = "en";

        FeignException notFoundException = mock(FeignException.class);
        when(notFoundException.status()).thenReturn(400);

        when(peopleFeignClient.getPopularPeopleFetch(page, language)).thenThrow(notFoundException);


        PageNotFoundException exception = assertThrows(
                PageNotFoundException.class, () -> peopleService.getPopularPeople(page, language));


        assertEquals("Page not found: " + page, exception.getMessage());
    }

    @Test
    public void testPopularPeopleLanguageNotFoundError() {
        int page = 1;
        String language = "uk";

        assertThrows(LanguagueNotFoundException.class, () -> peopleService.getPopularPeople(page, language));

    }

    @Test
    public void testPopularPeopleOtherException() {
        int page = 1;
        String language = "en";

        FeignException exception = mock(FeignException.class);
        when(exception.status()).thenReturn(500);

        when(peopleFeignClient.getPopularPeopleFetch(page, language)).thenThrow(exception);

        assertThrows(RuntimeException.class, () -> peopleService.getPopularPeople(page, language));
    }
}
