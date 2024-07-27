package ru.luttsev.authservice.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String roleId) {
        super("Role '%s' not found.".formatted(roleId));
    }
}
