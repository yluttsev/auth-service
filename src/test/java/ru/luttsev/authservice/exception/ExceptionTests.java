package ru.luttsev.authservice.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.request.TokenRefreshRequest;
import ru.luttsev.authservice.service.AppUserService;
import ru.luttsev.authservice.service.RefreshTokenService;
import ru.luttsev.authservice.service.RoleService;
import ru.luttsev.authservice.service.impl.AuthServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionTests {

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private RoleService roleService;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Test
    @DisplayName("Выброс исключения RefreshTokenNotFoundException")
    void testRefreshTokenNotFoundExceptionThrowing() {
        String invalidToken = "INVALID_TOKEN";
        when(refreshTokenService.getByToken(invalidToken)).thenThrow(RefreshTokenNotFoundException.class);

        assertThrows(RefreshTokenNotFoundException.class, () -> refreshTokenService.getByToken(invalidToken));
    }

    @Test
    @DisplayName("Выброс исключения RoleNotFoundException")
    void testRoleNotFoundExceptionThrowing() {
        String invalidRoleId = "IVALID_ROLE_ID";
        when(roleService.getById(invalidRoleId)).thenThrow(RoleNotFoundException.class);

        assertThrows(RoleNotFoundException.class, () -> roleService.getById(invalidRoleId));
    }

    @Test
    @DisplayName("Выброс исключения TokenRefreshException")
    void testTokenRefreshExceptionThrowing() {
        assertThrows(TokenRefreshException.class, () -> authServiceImpl.refreshToken(new TokenRefreshRequest(null)));
    }

    @Test
    @DisplayName("Выброс исключения UserAlreadyExistsException")
    void testUserAlreadyExistsExceptionThrowing() {
        when(appUserService.isLoginExists("login")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class,
                () -> authServiceImpl.signUp(SignUpRequest.builder()
                        .login("login")
                        .password("password")
                        .email("email@inbox.ru")
                        .build()));
    }

}
