package com.mistica.EducarTransformar.model.DTO;

import com.mistica.EducarTransformar.model.entity.Usuario;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class MateriaDTO {
    private Long id;
    private String nombreMateria;
    private LocalTime horarioEntrada;
    private LocalTime horarioSalida;
    private String anoEscolar;
    private List<AlumnoDTO> alumnos;
    private UsuarioDTO docente;
}
