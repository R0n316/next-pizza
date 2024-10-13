package ru.alex.nextpizzaapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.nextpizzaapi.database.entity.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    @Query("SELECT t FROM RefreshToken t WHERE t.user.email = :email")
    Optional<RefreshToken> findByUserEmail(String email);
}
