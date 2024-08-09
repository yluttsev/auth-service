package ru.luttsev.authservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.authservice.exception.RoleNotFoundException;
import ru.luttsev.authservice.model.entity.AppUser;
import ru.luttsev.authservice.model.entity.Role;
import ru.luttsev.authservice.repository.RoleRepository;
import ru.luttsev.authservice.service.RoleService;

import java.util.Set;

/**
 * Реализация сервиса для работы с ролями
 *
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getById(String roleId) {
        return roleRepository.findById(roleId).orElseThrow(
                () -> new RoleNotFoundException(roleId)
        );
    }

    @Override
    public Set<Role> getUserRoles(String login) {
        return roleRepository.findRolesByLogin(login);
    }

    @Override
    @Transactional
    public void addRolesToUser(AppUser user, Set<Role> roles) {
        user.setRoles(roles);
    }

}
