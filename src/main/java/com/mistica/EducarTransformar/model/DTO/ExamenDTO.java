package com.mistica.EducarTransformar.model.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ExamenDTO {
    private Long id;
    private Date fecha;
    private Double calificacion;
}
