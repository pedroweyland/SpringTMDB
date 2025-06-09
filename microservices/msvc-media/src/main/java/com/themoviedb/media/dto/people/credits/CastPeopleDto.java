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
public class CastPeopleDto extends BaseCreditsPeople {

    private String character;
    private Integer order;

    @JsonProperty("credit_id")
    private String creditId;

}
