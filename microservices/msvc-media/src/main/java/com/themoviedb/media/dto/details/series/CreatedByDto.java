package com.themoviedb.media.dto.details.series;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreatedByDto {

    private Integer id;
    private String name;
    private Integer gender;

    @JsonProperty("original_name")
    private String originalName;

    @JsonProperty("profile_path")
    private String profilePath;

    @JsonProperty("credit_id")
    private String creditId;
}
