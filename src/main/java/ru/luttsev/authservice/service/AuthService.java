package ru.luttsev.authservice.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import ru.luttsev.authservice.model.payload.request.SignInRequest;
import ru.luttsev.authservice.model.payload.response.SignInResponse;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.response.SignUpResponse;
import ru.luttsev.authservice.model.payload.response.TokenRefreshResponse;

import javax.security.auth.login.CredentialException;

public interface AuthService {

    SignUpResponse signUp(SignUpRequest signUpRequest);

    ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest) throws CredentialException;

    ResponseEntity<TokenRefreshResponse> refreshToken(HttpServletRequest httpServletRequest);

    ResponseCookie createRefreshTokenCookie(String refreshToken);

    Cookie getRefreshTokenCookie(HttpServletRequest httpServletRequest);

}
