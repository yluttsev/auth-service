package ru.luttsev.authservice.service;

import ru.luttsev.authservice.model.entity.RefreshToken;

import java.util.UUID;

public interface RefreshTokenService {

    RefreshToken save(RefreshToken refreshToken);

    RefreshToken getByToken(String token);

    void deleteTokenByUserId(UUID login);

    boolean isExpired(RefreshToken token);

}
