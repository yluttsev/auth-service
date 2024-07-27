package ru.luttsev.authservice.service;

import ru.luttsev.authservice.model.entity.Role;

public interface RoleService {

    Role getById(String roleId);

    Role getUserRole(String login);

}
