package ru.luttsev.authservice.service;

import ru.luttsev.authservice.model.entity.RefreshToken;

import java.util.UUID;

/**
 * Сервис для работы с refresh токеном
 *
 * @author Yuri Luttsev
 */
public interface RefreshTokenService {

    /**
     * Сохранение refresh токена
     *
     * @param refreshToken {@link RefreshToken} сущность refresh токена
     * @return {@link RefreshToken} обновленная сущность refresh токена
     */
    RefreshToken save(RefreshToken refreshToken);

    /**
     * Получение сущности refresh токена по refresh токену из запроса
     *
     * @param token refresh токен
     * @return {@link RefreshToken} сущность refresh токенв
     */
    RefreshToken getByToken(String token);

    /**
     * Удаление токенов пользователя
     *
     * @param userId {@link UUID} ID пользователя
     */
    void deleteTokenByUserId(UUID userId);

    /**
     * Проверка истекшего токена
     *
     * @param token {@link RefreshToken} сущность refresh токена
     * @return true, если токен истек, иначе false
     */
    boolean isExpired(RefreshToken token);

}
