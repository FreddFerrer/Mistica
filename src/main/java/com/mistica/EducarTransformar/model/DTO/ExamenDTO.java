package com.mistica.EducarTransformar.model.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ExamenDTO {

    private Long id;
    private String nombre;
    private Date fecha;
    private MateriaDTO materia;
}
