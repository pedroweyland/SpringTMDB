package com.themoviedb.media.dto.people;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDetailDto {

    private Boolean adult;
    private String biography;
    private String birthay;
    private String deathday;
    private Integer gender;
    private String homepage;
    private Integer id;

    private String name;

    private Double popularity;

    @JsonProperty("imdb_id")
    private String imdbId;

    @JsonProperty("known_for_department")
    private String knownForDepartment;

    @JsonProperty("place_of_birth")
    private String placeOfBirth;

    @JsonProperty("profile_path")
    private String profilePath;

    @JsonProperty("also_known_as")
    private String[] alsoKnownAs;
}
