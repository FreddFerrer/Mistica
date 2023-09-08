package com.mistica.EducarTransformar.model.DTO.request;

import com.mistica.EducarTransformar.model.entity.RolUsuario;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UsuarioRegisterRequest {
    @NotEmpty(message = "campo username obligatorio")
    private String username;
    @NotEmpty(message = "campo email obligatorio")
    @Email
    private String email;
    @NotEmpty(message = "campo contraseña obligatorio")
    private String password;
    @NotEmpty(message = "campo nombre obligatorio")
    private String nombre;
    @NotEmpty(message = "campo apellido obligatorio")
    private String apellido;
    private RolUsuario rol;
}
