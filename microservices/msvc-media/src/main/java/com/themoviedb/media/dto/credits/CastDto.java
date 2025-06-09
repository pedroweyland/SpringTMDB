package com.themoviedb.media.dto.credits;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CastDto {

    private Integer id;
    private Boolean adult;
    private Integer gender;
    private String name;
    private Double popularity;
    private String character;
    private String order;

    @JsonProperty("known_for_department")
    private String knownFormDepartment;

    @JsonProperty("original_name")
    private String original_name;

    @JsonProperty("profile_path")
    private String profilePath;

    @JsonProperty("cast_id")
    private Integer castId;

    @JsonProperty("credit_id")
    private String creditId;
}
