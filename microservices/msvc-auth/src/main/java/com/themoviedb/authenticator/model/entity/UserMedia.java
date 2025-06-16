package com.themoviedb.authenticator.model.entity;

import com.themoviedb.authenticator.model.ListType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "user_media",
    uniqueConstraints = @UniqueConstraint(
            columnNames = {"id_user", "id_media", "list_type"}
    )
)
public class UserMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_media", nullable = false)
    private Media media;

    @Enumerated(EnumType.STRING)
    private ListType listType;
}
