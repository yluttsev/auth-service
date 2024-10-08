package ru.luttsev.authservice.model.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.luttsev.authservice.model.entity.AppUser;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Детали аутентифицированного пользователя
 *
 * @author Yuri Luttsev
 */
@RequiredArgsConstructor
public class AppUserDetailsImpl implements UserDetails {

    private final AppUser appUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return appUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
