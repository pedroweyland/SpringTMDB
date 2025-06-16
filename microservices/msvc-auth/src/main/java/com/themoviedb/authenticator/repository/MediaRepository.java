package com.themoviedb.authenticator.repository;

import com.themoviedb.authenticator.model.ListType;
import com.themoviedb.authenticator.model.entity.Media;
import com.themoviedb.authenticator.model.entity.User;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Integer> {

    Optional<Media> findByIdMedia(Integer idMedia);

    @Query("""
       SELECT m FROM Media m
       JOIN m.userMediaList um
       WHERE um.user = :user AND um.listType = :listType
       """)
    List<Media> findByUserAndListType(@Param("user") User user, @Param("listType") ListType listType);

}
