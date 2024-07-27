package ru.luttsev.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.authservice.exception.RoleNotFoundException;
import ru.luttsev.authservice.model.entity.Role;
import ru.luttsev.authservice.repository.RoleRepository;
import ru.luttsev.authservice.service.RoleService;

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
    public Role getUserRole(String login) {
        return roleRepository.findRolesByLogin(login);
    }

}
