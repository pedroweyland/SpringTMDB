package com.themoviedb.media.dto.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductionCountriesDto {

    private String name;

    @JsonProperty("iso_3166_1")
    private String isoCode;

}
