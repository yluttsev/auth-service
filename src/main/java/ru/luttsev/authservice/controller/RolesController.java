package ru.luttsev.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
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

/**
 * Контроллер для установки ролей пользователей
 *
 * @author Yuri Luttsev
 */
@Tag(name = "roles", description = "API для установки ролей пользователей")
@RestController
@RequestMapping("/auth/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RoleService roleService;

    private final AppUserService appUserService;

    /**
     * Устанавливает роли пользователю
     *
     * @param addRoleRequest {@link AddRoleRequest} запрос на установку ролей пользователю
     * @return {@link UserRolesResponse} ответ со списком ролей пользователя
     */
    @Operation(summary = "Установка ролей пользователю", method = "PUT")
    @ApiResponse(
            responseCode = "200",
            description = "Успешная установка ролей пользователю",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = UserRolesResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Не найдена указанная роль",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = ProblemDetail.class
                    )
            )
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/save")
    public UserRolesResponse addRoleToUser(@RequestBody AddRoleRequest addRoleRequest) {
        roleService.addRolesToUser(appUserService.getUserByLogin(addRoleRequest.getUser()),
                addRoleRequest.getRoles().stream().map(roleService::getById).collect(Collectors.toSet()));
        return new UserRolesResponse(addRoleRequest.getUser(),
                roleService.getUserRoles(addRoleRequest.getUser()).stream().map(Role::getId).toList());
    }

}
