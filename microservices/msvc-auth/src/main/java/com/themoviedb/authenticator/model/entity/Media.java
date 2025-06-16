package com.themoviedb.authenticator.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoviedb.authenticator.model.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private Integer idMedia;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @JsonProperty("media_type")
    private MediaType mediaType;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("release_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("user_media_list")
    @OneToMany(mappedBy = "media", fetch = FetchType.LAZY)
    private List<UserMedia> userMediaList;
}
