package com.themoviedb.media.dto.movie;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoviedb.media.dto.details.GenreDto;
import com.themoviedb.media.dto.details.ProductionCompaniesDto;
import com.themoviedb.media.dto.details.ProductionCountriesDto;
import com.themoviedb.media.dto.details.SpokenLanguagesDto;
import com.themoviedb.media.dto.details.movies.BelongsToCollection;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailDto extends MovieDto {

    private Integer budget;
    private Integer revenue;
    private Integer runtime;

    private String homepage;
    private String status;
    private String tagline;

    @JsonIgnore
    private List<Integer> genreIds;

    @JsonProperty("belongs_to_collection")
    private BelongsToCollection belongsToCollection;

    @JsonProperty("imdb_id")
    private String imdbId;

    @JsonProperty("origin_country")
    private List<String> originCountry;

    @JsonProperty("production_companies")
    private List<ProductionCompaniesDto> productionCompanies;

    @JsonProperty("production_countries")
    private List<ProductionCountriesDto> productionCountries;

    @JsonProperty("spoken_languages")
    private List<SpokenLanguagesDto> spokenLanguagesDto;

    private List<GenreDto> genres;
}

