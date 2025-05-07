package com.themoviedb.media.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoviedb.media.dto.details.*;
import com.themoviedb.media.dto.details.series.CreatedByDto;
import com.themoviedb.media.dto.details.series.LastEpisodeToAir;
import com.themoviedb.media.dto.details.series.NetworkDto;
import com.themoviedb.media.dto.details.series.SeasonDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SerieDetailDto {

    private Integer id;
    private Boolean adult;
    private String name;
    private String homepage;
    private String overview;
    private String status;
    private String tagline;
    private String type;
    private Double popularity;

    private List<GenreDto> genres;
    private List<String> languages;
    private List<NetworkDto> networks;
    private List<SeasonDto> seasons;

    @JsonProperty("spoken_languages")
    private List<SpokenLanguagesDto> spokenLanguagesDto;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("created_by")
    private List<CreatedByDto> createdBy;

    @JsonProperty("episode_run_time")
    private List<Integer> episodeRunTime;

    @JsonProperty("first_air_date")
    private String firstAirDate;

    @JsonProperty("in_production")
    private Boolean inProduction;

    @JsonProperty("last_air_date")
    private String lastAirDate;

    @JsonProperty("last_episode_to_air")
    private LastEpisodeToAir lastEpisodeToAir;

    @JsonProperty("next_episode_to_air")
    private String nextEpisodeToAir;

    @JsonProperty("number_of_episodes")
    private Integer numberOfEpisodes;

    @JsonProperty("number_of_seasons")
    private Integer numberOfSeasons;

    @JsonProperty("origin_country")
    private List<String> originCountry;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_name")
    private String originalName;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("production_companies")
    private List<ProductionCompaniesDto> productionCompanies;

    @JsonProperty("production_countries")
    private List<ProductionCountriesDto> productionCountries;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

}
