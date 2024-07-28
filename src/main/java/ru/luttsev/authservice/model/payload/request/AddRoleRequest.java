package ru.luttsev.authservice.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddRoleRequest {

    private String user;

    private List<String> roles;

}
