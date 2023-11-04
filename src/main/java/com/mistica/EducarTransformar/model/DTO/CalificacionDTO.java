package com.mistica.EducarTransformar.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CalificacionDTO {
    private Long id;
    private AlumnoDTO alumno;
    private Double nota;
    private ExamenDTO examen;
}
