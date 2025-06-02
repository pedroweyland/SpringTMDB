package com.themoviedb.media.dto.serie;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoviedb.media.dto.details.*;
import com.themoviedb.media.dto.details.series.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SerieDetailDto extends SerieDto {

    private String homepage;
    private String status;
    private String tagline;
    private String type;

    private List<GenreDto> genres;
    private List<String> languages;
    private List<NetworkDto> networks;
    private List<SeasonDto> seasons;

    @JsonIgnore
    private List<Integer> genreIds;

    @JsonProperty("spoken_languages")
    private List<SpokenLanguagesDto> spokenLanguagesDto;

    @JsonProperty("created_by")
    private List<CreatedByDto> createdBy;

    @JsonProperty("episode_run_time")
    private List<Integer> episodeRunTime;

    @JsonProperty("in_production")
    private Boolean inProduction;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("last_air_date")
    private LocalDate lastAirDate;

    @JsonProperty("last_episode_to_air")
    private LastEpisodeToAir lastEpisodeToAir;

    @JsonProperty("next_episode_to_air")
    private LastEpisodeToAir nextEpisodeToAir;

    @JsonProperty("number_of_episodes")
    private Integer numberOfEpisodes;

    @JsonProperty("number_of_seasons")
    private Integer numberOfSeasons;

    @JsonProperty("production_companies")
    private List<ProductionCompaniesDto> productionCompanies;

    @JsonProperty("production_countries")
    private List<ProductionCountriesDto> productionCountries;

}
