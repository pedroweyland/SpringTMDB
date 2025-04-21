package com.themoviedb.media.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoviedb.media.dto.details.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieDetailDto {

    private Integer id;
    private Integer budget;
    private Integer revenue;
    private Integer runtime;

    private Double popularity;

    private Boolean adult;
    private Boolean video;

    private String title;
    private String homepage;
    private String overview;
    private String status;
    private String tagline;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("belongs_to_collection")
    private BelongsToCollection belongsToCollection;

    @JsonProperty("imdb_id")
    private String imdbId;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("origin_country")
    private List<String> originCountry;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("release_date")
    private LocalDate releaseDate;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Double voteCount;

    @JsonProperty("production_companies")
    private List<ProductionCompaniesDto> productionCompanies;

    @JsonProperty("production_countries")
    private List<ProductionCountriesDto> productionCountries;

    @JsonProperty("spoken_languages")
    private List<SpokenLanguagesDto> spokenLanguagesDto;

    private List<GenreDto> genres;

}
