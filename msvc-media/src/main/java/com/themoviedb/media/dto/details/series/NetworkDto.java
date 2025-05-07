package com.themoviedb.media.dto.details.series;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NetworkDto {

    private Integer id;
    private String name;

    @JsonProperty("logo_path")
    private String logoPath;

    @JsonProperty("origin_country")
    private String originCountry;
}
