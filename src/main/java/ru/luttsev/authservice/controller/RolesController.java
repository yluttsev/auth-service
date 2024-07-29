package ru.luttsev.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.authservice.model.entity.Role;
import ru.luttsev.authservice.model.payload.request.AddRoleRequest;
import ru.luttsev.authservice.model.payload.response.UserRolesResponse;
import ru.luttsev.authservice.service.AppUserService;
import ru.luttsev.authservice.service.RoleService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RoleService roleService;

    private final AppUserService appUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/save")
    public UserRolesResponse addRoleToUser(@RequestBody AddRoleRequest addRoleRequest) {
        roleService.addRolesToUser(appUserService.getUserByLogin(addRoleRequest.getUser()),
                addRoleRequest.getRoles().stream().map(roleService::getById).collect(Collectors.toSet()));
        return new UserRolesResponse(addRoleRequest.getUser(),
                roleService.getUserRoles(addRoleRequest.getUser()).stream().map(Role::getId).toList());
    }

}
