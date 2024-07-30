package ru.luttsev.authservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.luttsev.authservice.exception.TokenRefreshException;
import ru.luttsev.authservice.exception.UserAlreadyExistsException;
import ru.luttsev.authservice.model.entity.AppUser;
import ru.luttsev.authservice.model.entity.RefreshToken;
import ru.luttsev.authservice.model.payload.request.SignInRequest;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.request.TokenRefreshRequest;
import ru.luttsev.authservice.model.payload.response.SignUpResponse;
import ru.luttsev.authservice.model.payload.response.TokenResponse;
import ru.luttsev.authservice.service.AppUserService;
import ru.luttsev.authservice.service.AuthService;
import ru.luttsev.authservice.service.RefreshTokenService;
import ru.luttsev.authservice.service.RoleService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserService appUserService;

    private final RoleService roleService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder bcryptPasswordEncoder;

    @Override
    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if (appUserService.isLoginExists(signUpRequest.getLogin())) {
            throw new UserAlreadyExistsException(signUpRequest.getLogin());
        }
        if (appUserService.isEmailExists(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException(signUpRequest.getEmail());
        }

        AppUser createdAppUser = AppUser.builder()
                .login(signUpRequest.getLogin())
                .email(signUpRequest.getEmail())
                .password(bcryptPasswordEncoder.encode(signUpRequest.getPassword()))
                .build();
        createdAppUser.getRoles().add(roleService.getById("ROLE_USER"));
        appUserService.save(createdAppUser);
        return new SignUpResponse("The user '%s' has been successfully registered.".formatted(signUpRequest.getLogin()));
    }

    @Override
    @Transactional
    public TokenResponse signIn(SignInRequest signInRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getLogin(), signInRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(auth);

        AppUser user = appUserService.getUserByLogin(signInRequest.getLogin());
        String accessToken = jwtService.generateAccessToken(user.getLogin());
        String refreshToken = jwtService.generateRefreshToken(user.getLogin());
        Date refreshExpiration = jwtService.getTokenExpiration(refreshToken);
        refreshTokenService.deleteTokenByUserId(user.getId());
        refreshTokenService.save(
                RefreshToken.builder()
                        .user(user)
                        .token(refreshToken)
                        .expirationDate(LocalDateTime.ofInstant(refreshExpiration.toInstant(), ZoneId.systemDefault()))
                        .build());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredAt(jwtService.getTokenExpiration(accessToken).getTime())
                .build();
    }

    @Override
    @Transactional
    public TokenResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        String refreshToken = tokenRefreshRequest.getRefreshToken();
        if (refreshToken != null) {
            RefreshToken refreshTokenEntity = refreshTokenService.getByToken(refreshToken);
            if (!refreshTokenService.isExpired(refreshTokenEntity) && !refreshTokenEntity.isRevoked()) {
                String accessToken = jwtService.generateAccessToken(refreshTokenEntity.getUser().getLogin());
                Date accessTokenExpiration = jwtService.getTokenExpiration(accessToken);
                return new TokenResponse(accessToken, refreshToken, accessTokenExpiration.getTime());
            }
            throw new TokenRefreshException("Refresh token was expired.");
        }
        throw new TokenRefreshException("Invalid refresh token.");
    }

}
