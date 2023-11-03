package com.mistica.EducarTransformar.model.DTO;

import com.mistica.EducarTransformar.model.entity.Alumno;
import lombok.Data;

@Data
public class CalificacionDTO {
    private Long id;
    private Alumno alumno;
    private Double nota;
}
