package com.themoviedb.media;

import com.themoviedb.media.dto.people.PeopleListDto;
import com.themoviedb.media.dto.people.PersonDetailDto;
import com.themoviedb.media.dto.people.PersonDto;
import com.themoviedb.media.dto.people.credits.CreditsPeopleDto;

import java.util.List;

public abstract class BasePeople {

    protected static PeopleListDto getPeopleListDto() {
        return PeopleListDto.builder()
                .page(1)
                .results(List.of(getPersonDto()))
                .totalPages(1)
                .totalResults(0)
                .build();
    }

    protected static PersonDto getPersonDto() {
        return PersonDto.builder()
                .adult(false)
                .gender(1)
                .id(1)
                .name("test name")
                .popularity(1.0)
                .profilePath("test path")
                .knownForDepartment("test department")
                .knownFor(List.of())
                .build();
    }

    protected static PersonDetailDto getPersonDetailDto() {
        return PersonDetailDto.builder()
                .id(1)
                .adult(false)
                .name("test name")
                .biography("test biography")
                .birthay("test birthay")
                .deathday("test deathday")
                .gender(1)
                .homepage("test homepage")
                .popularity(1.0)
                .imdbId("test imdbId")
                .knownForDepartment("test knownForDepartment")
                .placeOfBirth("test placeOfBirth")
                .profilePath("test profilePath")
                .build();
    }

    protected static CreditsPeopleDto getCreditsPeopleDto() {
        return CreditsPeopleDto.builder()
                .cast(List.of())
                .crew(List.of())
                .id("1")
                .build();
    }
}
