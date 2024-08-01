package ru.luttsev.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.authservice.model.entity.Role;
import ru.luttsev.authservice.model.payload.response.UserRolesResponse;
import ru.luttsev.authservice.service.RoleService;

import java.util.Set;

/**
 * Контроллер для просмотра ролей пользователей
 *
 * @author Yuri Luttsev
 */
@Tag(name = "user-role", description = "API для просмотра ролей пользователей")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserRoleController {

    private final RoleService roleService;

    /**
     * Показывает роли текущего пользователя
     *
     * @param userDetails {@link UserDetails} детали пользователя
     * @return {@link UserRolesResponse} ответ со списком ролей пользователя
     */
    @Operation(summary = "Просмотр ролей текущего пользователя", method = "GET")
    @ApiResponse(
            responseCode = "200",
            description = "Успешное получение ролей текущего пользователя",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = UserRolesResponse.class
                    )
            )
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user-role")
    public UserRolesResponse userRole(@AuthenticationPrincipal UserDetails userDetails) {
        return new UserRolesResponse(userDetails.getUsername(), userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
    }

    /**
     * Показывает роли пользователя по логину
     *
     * @param userLogin логин пользователя
     * @return {@link UserRolesResponse} ответ со списком ролей пользователя
     */
    @Operation(summary = "Просмотр ролей пользователя по его логину", method = "GET")
    @ApiResponse(
            responseCode = "200",
            description = "Успешное получение ролей пользователя",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = UserRolesResponse.class
                    )
            )
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user-role/{login}")
    public UserRolesResponse userRoleByLogin(@PathVariable("login")
                                             @Parameter(description = "Логин пользователя", required = true) String userLogin) {
        Set<Role> userRoles = roleService.getUserRoles(userLogin);
        return new UserRolesResponse(userLogin, userRoles.stream().map(Role::getId).toList());
    }

}
