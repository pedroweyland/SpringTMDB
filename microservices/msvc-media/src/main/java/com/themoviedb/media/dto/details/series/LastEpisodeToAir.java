package com.themoviedb.media.dto.details.series;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LastEpisodeToAir {

    private Integer id;
    private String name;
    private String overview;
    private Integer runtime;

    @JsonProperty("episode_type")
    private String episodeType;

    @JsonProperty("production_code")
    private String productionCode;

    @JsonProperty("season_number")
    private Integer seasonNumber;

    @JsonProperty("episode_number")
    private Integer episodeNumber;

    @JsonProperty("still_path")
    private String stillPath;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

    @JsonProperty("air_date")
    private String airDate;

    @JsonProperty("show_id")
    private Integer showId;
}
