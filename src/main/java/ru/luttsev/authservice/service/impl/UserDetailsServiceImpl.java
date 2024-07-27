package ru.luttsev.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.luttsev.authservice.model.entity.AppUser;
import ru.luttsev.authservice.model.security.AppUserDetailsImpl;
import ru.luttsev.authservice.service.AppUserService;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserService.getUserByLogin(username);
        return new AppUserDetailsImpl(user);
    }

}
