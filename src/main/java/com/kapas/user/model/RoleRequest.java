package com.kapas.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RoleRequest {

    @NotBlank(message = "Role Name can not be blank")
    @Size(min= 2, max = 15, message = "Role Name should be of characters 2 to 15")
    private String roleName;

    @NotBlank(message = "Desciption can not be blank")
    private String description;

}
