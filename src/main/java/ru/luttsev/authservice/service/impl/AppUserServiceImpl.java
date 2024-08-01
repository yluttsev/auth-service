package ru.luttsev.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.luttsev.authservice.model.entity.AppUser;
import ru.luttsev.authservice.repository.AppUserRepository;
import ru.luttsev.authservice.service.AppUserService;

/**
 * Реализация сервиса для работы с пользователями
 *
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public AppUser getUserByLogin(String login) {
        return appUserRepository.findByLogin(login).orElseThrow(
                () -> new UsernameNotFoundException("User '%s' not found.".formatted(login))
        );
    }

    @Override
    public boolean isLoginExists(String login) {
        return appUserRepository.checkLogin(login);
    }

    @Override
    public boolean isEmailExists(String email) {
        return appUserRepository.checkEmail(email);
    }

    @Override
    public void save(AppUser appUser) {
        appUserRepository.save(appUser);
    }

}
