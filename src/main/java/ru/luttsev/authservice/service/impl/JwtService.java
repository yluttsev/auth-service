package ru.luttsev.authservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.luttsev.authservice.model.entity.Role;
import ru.luttsev.authservice.service.RoleService;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RoleService roleService;

    @Value("${jwt.access.secret}")
    private String accessSecret;

    @Value("${jwt.refresh.secret}")
    private String refreshSecret;

    @Value("${jwt.access.expiration}")
    private long accessExpirationTime;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpirationTime;

    public String generateAccessToken(String username) {
        return JWT.create()
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date().toInstant().plus(accessExpirationTime, ChronoUnit.MILLIS))
                .withSubject(username)
                .withClaim("roles", roleService.getUserRoles(username).stream().map(Role::getId).toList())
                .sign(Algorithm.HMAC256(accessSecret));
    }

    public String generateRefreshToken(String username) {
        return JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date().toInstant().plus(refreshExpirationTime, ChronoUnit.MILLIS))
                .withSubject(username)
                .sign(Algorithm.HMAC256(refreshSecret));
    }

    public Date getTokenExpiration(String token) {
        return JWT.decode(token)
                .getExpiresAt();
    }

    public boolean validateAccessToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(accessSecret)).build();
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUsernameFromAccessToken(String token) {
        return JWT.decode(token).getSubject();
    }

}
