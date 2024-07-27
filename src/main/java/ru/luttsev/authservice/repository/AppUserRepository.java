package ru.luttsev.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.luttsev.authservice.model.entity.AppUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByLogin(String login);

    Optional<AppUser> findByEmail(String email);

    @Query("select exists (select u from AppUser u where u.login = :login)")
    boolean checkLogin(@Param("login") String login);

    @Query("select exists (select u from AppUser u where u.email = :email)")
    boolean checkEmail(@Param("email") String email);

}
