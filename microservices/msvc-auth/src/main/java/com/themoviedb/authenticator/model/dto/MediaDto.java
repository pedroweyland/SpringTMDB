package com.themoviedb.authenticator.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoviedb.authenticator.model.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaDto {

    private Integer id;

    @JsonAlias({"title", "name"})
    private String title;
    private String overview;

    @JsonProperty("media_type")
    private MediaType mediaType;

    @JsonAlias({"original_title", "original_name"})
    private String originalTitle;

    @JsonAlias({"release_date", "first_air_date"})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("vote_average")
    private Double voteAverage;

}
