package ru.luttsev.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.authservice.model.entity.Role;
import ru.luttsev.authservice.model.payload.response.UserRoleResponse;
import ru.luttsev.authservice.service.RoleService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RolesController {

    private final RoleService roleService;

    @PreAuthorize("@hasRole('USER')")
    @GetMapping("/user-roles")
    public UserRoleResponse userRoles(@AuthenticationPrincipal String userLogin) {
        Role userRole = roleService.getUserRole(userLogin);
        return new UserRoleResponse(userLogin, userRole.getId());
    }

}
