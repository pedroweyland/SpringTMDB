package com.themoviedb.media.dto.credits;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CrewDto {

    private Integer id;
    private Boolean adult;
    private Integer gender;
    private String name;
    private Double popularity;
    private String job;
    private String department;

    @JsonProperty("known_for_department")
    private String knownFormDepartment;

    @JsonProperty("original_name")
    private String originalName;

    @JsonProperty("credit_id")
    private String creditId;

    @JsonProperty("profile_path")
    private String profilePath;

}