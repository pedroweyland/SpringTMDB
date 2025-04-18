package com.themoviedb.media.model;

//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.util.Date;

/*
@Data
@Entity
@Builder
@Table(name = "media")
@AllArgsConstructor
@NoArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "media_id")
    private int mediaId;

    @Enumerated(EnumType.STRING)
    private TipoMedia type;
    private String title;

    @Column(name = "original_title")
    private String originalTitle;

    @Column(name = "release_date")
    private Date releaseDate;

    private String overview;

    @Column (name = "poster_path")
    private String posterPath;

    @Column (name = "vote_average")
    private Double voteAverage;
}
*/