package com.mistica.EducarTransformar.model.DTO;

import lombok.Data;

@Data
public class AlumnoExamenDTO {
    private Long alumnoId;
    private String nombreAlumno;
    private Long examenId;
    private Double calificacion;

    public AlumnoExamenDTO(Long id, String s, Long id1, Double calificacion) {
    }
}
