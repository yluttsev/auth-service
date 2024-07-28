package ru.luttsev.authservice.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRolesResponse {

    private String user;

    private Collection<String> roles;

}
