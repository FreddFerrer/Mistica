package com.mistica.EducarTransformar.model.DTO;

import com.mistica.EducarTransformar.model.entity.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {
    private String username;
    private String email;
    private String nombre;
    private String apellido;
    private Rol rol;
}
