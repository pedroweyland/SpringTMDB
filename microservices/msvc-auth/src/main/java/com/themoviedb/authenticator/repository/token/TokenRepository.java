package com.themoviedb.authenticator.repository.token;


import com.themoviedb.authenticator.repository.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    // Trae todos los tokens válidos (no expirados ni revocados) de un usuario
    @Query("SELECT t FROM Token t " +
            "WHERE t.user = :user AND (t.expired = false AND t.revoked = false)")
    List<Token> findAllValidTokensByUser(User user);

    // Busca un token específico por su valor (por ejemplo para logout o refresh)
    Optional<Token> findByToken(String token);

}
