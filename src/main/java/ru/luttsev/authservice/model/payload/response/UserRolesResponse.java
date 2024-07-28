package ru.luttsev.authservice.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRolesResponse {

    private String user;

    private List<String> roles;

}
