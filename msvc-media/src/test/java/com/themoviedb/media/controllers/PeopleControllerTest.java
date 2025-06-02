package com.themoviedb.media.controllers;

import com.themoviedb.media.BasePeople;
import com.themoviedb.media.dto.people.PeopleListDto;
import com.themoviedb.media.dto.people.PersonDetailDto;
import com.themoviedb.media.dto.people.credits.CreditsPeopleDto;
import com.themoviedb.media.service.PeopleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PeopleControllerTest extends BasePeople {

    @Mock
    private PeopleService peopleService;

    @InjectMocks
    private PeopleController peopleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        peopleController = new PeopleController(peopleService);
    }

    @Test
    public void getPopularPeopleSuccess() {
        PeopleListDto peopleListDto = getPeopleListDto();

        when(peopleService.getPopularPeople(1, "en")).thenReturn(peopleListDto);

        ResponseEntity<PeopleListDto> response = peopleController.getPopularPeople(1, "en");

        verify(peopleService, times(1)).getPopularPeople(1, "en");
        assertEquals(response.getBody(), peopleListDto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getPersonDetailSuccess() {
        PersonDetailDto personDetail = getPersonDetailDto();

        when(peopleService.getPersonDetail("en", 1)).thenReturn(personDetail);

        ResponseEntity<PersonDetailDto> response = peopleController.getPersonDetail("en", 1);

        verify(peopleService, times(1)).getPersonDetail("en", 1);
        assertEquals(response.getBody(), personDetail);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getCreditsPersonSuccess() {
        CreditsPeopleDto creditsPeopleDto = getCreditsPeopleDto();

        when(peopleService.getCreditsPersonMovie("es", 1)).thenReturn(creditsPeopleDto);

        ResponseEntity<CreditsPeopleDto> response = peopleController.getCreditsPersonMovie("es", 1);

        verify(peopleService, times(1)).getCreditsPersonMovie("es", 1);
        assertEquals(response.getBody(), creditsPeopleDto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getCreditsPersonTvSuccess() {
        CreditsPeopleDto creditsPeopleDto = getCreditsPeopleDto();

        when(peopleService.getCreditsPersonTvShow("es", 1)).thenReturn(creditsPeopleDto);

        ResponseEntity<CreditsPeopleDto> response = peopleController.getCreditsPersonTvShow("es", 1);

        verify(peopleService, times(1)).getCreditsPersonTvShow("es", 1);
        assertEquals(response.getBody(), creditsPeopleDto);
        assertEquals(200, response.getStatusCodeValue());
    }
}
