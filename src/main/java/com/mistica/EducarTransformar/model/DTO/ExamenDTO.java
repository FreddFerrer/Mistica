package com.mistica.EducarTransformar.model.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ExamenDTO {

    private MateriaDTO materia;
    private String nombre;
    private Date fecha;
}
