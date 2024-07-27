package ru.luttsev.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.luttsev.authservice.model.entity.AppUser;
import ru.luttsev.authservice.model.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, UUID> {

    @Query("select t from RefreshToken t where t.token = :token")
    Optional<RefreshToken> findByToken(@Param("token") String token);

    @Query("delete RefreshToken r_t where r_t.user = :user")
    @Modifying
    void deleteByUser(@Param("user") AppUser user);

}
