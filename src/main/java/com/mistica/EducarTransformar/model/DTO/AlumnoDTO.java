package com.mistica.EducarTransformar.model.DTO;

import com.mistica.EducarTransformar.model.entity.RolUsuario;
import com.mistica.EducarTransformar.model.entity.Usuario;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AlumnoDTO {
    private Integer legajo;
    private String nombre;
    private String apellido;
    private RolUsuario rol;
    private List<CalificacionDTO> calificaciones;
    private List<AsistenciaDTO> asistencias;
    private UsuarioDTO usuario;


}
