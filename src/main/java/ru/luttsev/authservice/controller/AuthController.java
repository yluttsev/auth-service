package ru.luttsev.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.authservice.model.payload.request.SignInRequest;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.request.TokenRefreshRequest;
import ru.luttsev.authservice.model.payload.response.SignUpResponse;
import ru.luttsev.authservice.model.payload.response.TokenResponse;
import ru.luttsev.authservice.service.AuthService;

import javax.security.auth.login.CredentialException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PutMapping("/signup")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    public TokenResponse signIn(@RequestBody SignInRequest signInRequest) throws CredentialException {
        return authService.signIn(signInRequest);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@RequestBody TokenRefreshRequest request) {
        return authService.refreshToken(request);
    }

}
