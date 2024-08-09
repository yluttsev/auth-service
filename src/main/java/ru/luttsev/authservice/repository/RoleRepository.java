package ru.luttsev.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.luttsev.authservice.model.entity.Role;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    @Query("select a_u.roles from AppUser a_u where a_u.login = :login")
    Set<Role> findRolesByLogin(@Param("login") String login);

}
