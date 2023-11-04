package com.mistica.EducarTransformar.model.DTO;


import com.mistica.EducarTransformar.model.entity.RolUsuario;
import lombok.Data;

import java.util.List;

@Data
public class ListaAlumnosDTO {
    private Long id;
    private Integer legajo;
    private String nombre;
    private String apellido;
    private RolUsuario rol;
    private UsuarioDTO usuario;
    private List<MateriaDTO> materias;
    private List<PagoDTO> pagos;
}
