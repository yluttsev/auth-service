package ru.luttsev.authservice.service;

import ru.luttsev.authservice.model.entity.AppUser;

public interface AppUserService {

    AppUser getUserByLogin(String login);

    boolean isLoginExists(String login);

    boolean isEmailExists(String email);

    void save(AppUser appUser);

}
