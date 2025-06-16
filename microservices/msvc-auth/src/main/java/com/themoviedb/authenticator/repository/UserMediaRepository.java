package com.themoviedb.authenticator.repository;

import com.themoviedb.authenticator.model.ListType;
import com.themoviedb.authenticator.model.entity.Media;
import com.themoviedb.authenticator.model.entity.User;
import com.themoviedb.authenticator.model.entity.UserMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMediaRepository extends JpaRepository<UserMedia, Integer> {

    boolean existsByUserAndMediaAndListType(User user, Media media, ListType listType);

    Optional<UserMedia> findByUserAndMediaAndListType(User user, Media media, ListType listType);
}
