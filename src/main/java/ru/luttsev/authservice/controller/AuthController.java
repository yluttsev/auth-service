package ru.luttsev.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.authservice.model.payload.SignInRequest;
import ru.luttsev.authservice.model.payload.SignInResponse;
import ru.luttsev.authservice.model.payload.SignUpRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PutMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        return null;
    }

    @PostMapping("/signin")
    public SignInResponse signIn(@RequestBody SignInRequest signInRequest) {
        return null;
    }

}
