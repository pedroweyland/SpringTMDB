package com.themoviedb.media.dto.details.movies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BelongsToCollection {

    private Integer id;
    private String name;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath;
}
