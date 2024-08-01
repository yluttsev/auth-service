package ru.luttsev.authservice.service;

import ru.luttsev.authservice.model.entity.AppUser;

/**
 * Сервис для работы с пользователями
 *
 * @author Yuri Luttsev
 */
public interface AppUserService {

    /**
     * Получение пользователя по логину
     *
     * @param login лоигн пользователя
     * @return {@link AppUser} сущность пользователя
     */
    AppUser getUserByLogin(String login);

    /**
     * Проверка существования пользователя по логину
     *
     * @param login логин пользователя
     * @return true, если пользователь существует, иначе false
     */
    boolean isLoginExists(String login);

    /**
     * Проверка существования пользователя по email
     *
     * @param email email пользователя
     * @return true, если пользователь существует, иначе false
     */
    boolean isEmailExists(String email);

    /**
     * Сохранение пользователя
     *
     * @param appUser {@link AppUser} сущность пользователя
     */
    void save(AppUser appUser);

}
