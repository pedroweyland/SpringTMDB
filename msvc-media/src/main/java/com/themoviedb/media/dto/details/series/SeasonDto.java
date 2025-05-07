package com.themoviedb.media.dto.details.series;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SeasonDto {

    private Integer id;
    private String name;
    private String overview;

    @JsonProperty("air_date")
    private String airDate;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("episode_count")
    private Integer episodeCount;

    @JsonProperty("season_number")
    private Integer seasonNumber;

    @JsonProperty("vote_average")
    private Integer voteAverage;
}
