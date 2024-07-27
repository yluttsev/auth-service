package ru.luttsev.authservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.luttsev.authservice.exception.RefreshTokenNotFoundException;
import ru.luttsev.authservice.exception.TokenRefreshException;
import ru.luttsev.authservice.exception.UserAlreadyExistsException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail userAlreadyExists(UserAlreadyExistsException e) {
        return createProblemDetail(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail userNotFound(BadCredentialsException e) {
        return createProblemDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ProblemDetail invalidRefreshToken(RefreshTokenNotFoundException e) {
        return createProblemDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ProblemDetail refreshTokenExpired(TokenRefreshException e) {
        return createProblemDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String message) {
        return ProblemDetail.forStatusAndDetail(status, message);
    }

}
