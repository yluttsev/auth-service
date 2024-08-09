package ru.luttsev.authservice.service;

import ru.luttsev.authservice.model.entity.AppUser;
import ru.luttsev.authservice.model.entity.Role;

import java.util.Set;

/**
 * Сервис для работы с ролями пользователей
 *
 * @author Yuri Luttsev
 */
public interface RoleService {

    /**
     * Получение роли по ID
     *
     * @param roleId ID роли
     * @return {@link Role} сущность роли
     */
    Role getById(String roleId);

    /**
     * Получение ролей пользователя
     *
     * @param login логин пользователя
     * @return {@link Set} коллекция ролей пользователя
     */
    Set<Role> getUserRoles(String login);

    /**
     * Установка ролей пользователю
     *
     * @param user  {@link AppUser} сущность пользователя
     * @param roles {@link Set} коллекция ролей
     */
    void addRolesToUser(AppUser user, Set<Role> roles);

}
