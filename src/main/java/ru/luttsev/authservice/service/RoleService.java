package ru.luttsev.authservice.service;

import ru.luttsev.authservice.model.entity.AppUser;
import ru.luttsev.authservice.model.entity.Role;

import java.util.Set;

public interface RoleService {

    Role getById(String roleId);

    Set<Role> getUserRoles(String login);

    void addRolesToUser(AppUser user, Set<Role> roles);

}
