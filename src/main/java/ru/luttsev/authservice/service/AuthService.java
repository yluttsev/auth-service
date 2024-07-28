package ru.luttsev.authservice.service;

import ru.luttsev.authservice.model.payload.request.SignInRequest;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.request.TokenRefreshRequest;
import ru.luttsev.authservice.model.payload.response.SignUpResponse;
import ru.luttsev.authservice.model.payload.response.TokenResponse;

import javax.security.auth.login.CredentialException;

public interface AuthService {

    SignUpResponse signUp(SignUpRequest signUpRequest);

    TokenResponse signIn(SignInRequest signInRequest) throws CredentialException;

    TokenResponse refreshToken(TokenRefreshRequest tokenRefreshRequest);

}
