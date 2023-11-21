package com.mistica.EducarTransformar.model.DTO;

import com.mistica.EducarTransformar.model.entity.RolUsuario;
import lombok.Data;

import java.util.List;

@Data
public class AlumnoDTO {
    private Integer legajo;
    private String nombre;
    private String apellido;
    private RolUsuario rol;
    private UsuarioDTO usuario;
}
