package com.mistica.EducarTransformar.model.DTO;

import com.mistica.EducarTransformar.model.entity.Usuario;
import lombok.Data;

import java.util.List;

@Data
public class MateriaDTO {
    private Long id;
    private String nombreMateria;
    private String anoEscolar;
    private List<AlumnoDTO> alumnos;
    private Usuario docente;
}
