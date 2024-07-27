package ru.luttsev.authservice.exception;

public class RefreshTokenNotFoundException extends RuntimeException {

    public RefreshTokenNotFoundException() {
        super("Invalid refresh token.");
    }
}
