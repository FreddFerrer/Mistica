package com.mistica.EducarTransformar.model.DTO;

import lombok.Data;

@Data
public class CalificacionDTO {
    private Long id;
    private Double nota;
    private ExamenDTO examen;
}
