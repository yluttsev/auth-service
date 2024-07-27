package ru.luttsev.authservice.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;
import ru.luttsev.authservice.exception.TokenRefreshException;
import ru.luttsev.authservice.exception.UserAlreadyExistsException;
import ru.luttsev.authservice.model.entity.AppUser;
import ru.luttsev.authservice.model.entity.RefreshToken;
import ru.luttsev.authservice.model.payload.request.SignInRequest;
import ru.luttsev.authservice.model.payload.response.SignInResponse;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.response.SignUpResponse;
import ru.luttsev.authservice.model.payload.response.TokenRefreshResponse;
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

    @Value("${jwt.refresh.cookie-name}")
    private String refreshCookieName;

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
                .password(BCrypt.hashpw(signUpRequest.getPassword(), BCrypt.gensalt()))
                .role(roleService.getById("USER"))
                .build();
        appUserService.save(createdAppUser);
        return new SignUpResponse("The user '%s' has been successfully registered.".formatted(signUpRequest.getLogin()));
    }

    @Override
    @Transactional
    public ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getLogin(), signInRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(auth);

        AppUser user = appUserService.getUserByLogin(signInRequest.getLogin());
        String accessToken = jwtService.generateAccessToken(user.getLogin());
        String refreshToken = jwtService.generateRefreshToken(user.getLogin());
        Date refreshExpiration = jwtService.getTokenExpiration(refreshToken);
        refreshTokenService.deleteTokenByUser(user);
        refreshTokenService.save(
                RefreshToken.builder()
                        .user(user)
                        .token(refreshToken)
                        .expirationDate(LocalDateTime.ofInstant(refreshExpiration.toInstant(), ZoneId.systemDefault()))
                        .build());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, this.createRefreshTokenCookie(refreshToken).toString())
                .body(SignInResponse.builder()
                        .accessToken(accessToken)
                        .expiredAt(jwtService.getTokenExpiration(accessToken).getTime())
                        .build()
                );
    }

    @Override
    @Transactional
    public ResponseEntity<TokenRefreshResponse> refreshToken(HttpServletRequest httpServletRequest) {
        String cookieRefreshToken = this.getRefreshTokenCookie(httpServletRequest).getValue();
        if (cookieRefreshToken != null) {
            RefreshToken refreshToken = refreshTokenService.getByToken(cookieRefreshToken);
            if (!refreshTokenService.isExpired(refreshToken) && !refreshToken.isRevoked()) {
                String accessToken = jwtService.generateAccessToken(refreshToken.getUser().getLogin());
                Date accessTokenExpiration = jwtService.getTokenExpiration(accessToken);
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, this.createRefreshTokenCookie(cookieRefreshToken).toString())
                        .body(new TokenRefreshResponse(accessToken, accessTokenExpiration.getTime()));
            }
            throw new TokenRefreshException("Refresh token was expired.");
        }
        throw new TokenRefreshException("Invalid refresh token.");
    }

    @Override
    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(refreshCookieName, refreshToken).path("/auth/refresh-token").httpOnly(true).build();
    }

    @Override
    public Cookie getRefreshTokenCookie(HttpServletRequest httpServletRequest) {
        return WebUtils.getCookie(httpServletRequest, refreshCookieName);
    }

}
