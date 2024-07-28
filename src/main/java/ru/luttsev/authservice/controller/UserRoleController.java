package ru.luttsev.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.authservice.model.entity.Role;
import ru.luttsev.authservice.model.payload.response.UserRolesResponse;
import ru.luttsev.authservice.service.RoleService;

import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserRoleController {

    private final RoleService roleService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user-role")
    public UserRolesResponse userRole(@AuthenticationPrincipal String userPrincipal) {
        Set<Role> userRoles = roleService.getUserRoles(userPrincipal);
        return new UserRolesResponse(userPrincipal, userRoles.stream().map(Role::getId).toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user-role/{login}")
    public UserRolesResponse userRoleByLogin(@PathVariable("login") String userLogin) {
        Set<Role> userRoles = roleService.getUserRoles(userLogin);
        return new UserRolesResponse(userLogin, userRoles.stream().map(Role::getId).toList());
    }

}
