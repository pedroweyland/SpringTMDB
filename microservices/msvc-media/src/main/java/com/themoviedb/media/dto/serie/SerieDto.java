package com.themoviedb.media.dto.serie;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SerieDto {

    private Integer id;
    private String name;
    private String overview;
    private Double popularity;
    private Boolean adult;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("first_air_date")
    private LocalDate firstAirDate;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;

    @JsonProperty("origin_country")
    private List<String> originCountry;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_name")
    private String originalName;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

}
