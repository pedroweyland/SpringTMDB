package com.themoviedb.media.dto.people;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonDto {

    private Boolean adult;
    private Integer gender;
    private Integer id;

    private String name;
    private Double popularity;

    @JsonProperty("profile_path")
    private String profilePath;

    @JsonProperty("known_for_department")

    private String knownForDepartment;
    @JsonProperty("known_for")
    private List<KnowForDto> knownFor;

}
