package ru.luttsev.authservice.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String user) {
        super("User '%s' already exists.".formatted(user));
    }
}
