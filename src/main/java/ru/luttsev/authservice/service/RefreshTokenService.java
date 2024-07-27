package ru.luttsev.authservice.service;

import ru.luttsev.authservice.model.entity.AppUser;
import ru.luttsev.authservice.model.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken save(RefreshToken refreshToken);

    RefreshToken getByToken(String token);

    void deleteTokenByUser(AppUser user);

    boolean isExpired(RefreshToken token);

}
