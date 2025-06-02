package com.themoviedb.media.dto.people.credits;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CrewPeopleDto extends BaseCreditsPeople {

    private String department;
    private String job;

    @JsonProperty("credit_id")
    private String creditId;

}
