package com.themoviedb.media.dto.people.credits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CreditsPeopleDto {

    private List<CastPeopleDto> cast;
    private List<CrewPeopleDto> crew;
    private String id;
}
