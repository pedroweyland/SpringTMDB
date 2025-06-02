package com.themoviedb.media.controllers;

import com.themoviedb.media.dto.people.credits.CreditsPeopleDto;
import com.themoviedb.media.dto.people.PeopleListDto;
import com.themoviedb.media.dto.people.PersonDetailDto;
import com.themoviedb.media.service.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/media/people")
@RequiredArgsConstructor
public class PeopleController {

    private final PeopleService peopleService;

    @GetMapping("/popular")
    public ResponseEntity<PeopleListDto> getPopularPeople(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "en") String language
    ) {
        return ResponseEntity.ok(peopleService.getPopularPeople(page, language));
    }

    @GetMapping("/person/{idPerson}")
    public ResponseEntity<PersonDetailDto> getPersonDetail(
            @RequestParam(defaultValue = "en") String language,
            @PathVariable Integer idPerson
    ) {
        return ResponseEntity.ok(peopleService.getPersonDetail(language, idPerson));
    }

    @GetMapping("/person/{idPerson}/movie-credits")
    public ResponseEntity<CreditsPeopleDto> getCreditsPersonMovie(
            @RequestParam(defaultValue = "en") String language,
            @PathVariable Integer idPerson
    ) {
        return ResponseEntity.ok(peopleService.getCreditsPersonMovie(language, idPerson));
    }

    @GetMapping("/person/{idPerson}/tv-credits")
    public ResponseEntity<CreditsPeopleDto> getCreditsPersonTvShow(
            @RequestParam(defaultValue = "en") String language,
            @PathVariable Integer idPerson
    ) {
        return ResponseEntity.ok(peopleService.getCreditsPersonTvShow(language, idPerson));
    }
}
