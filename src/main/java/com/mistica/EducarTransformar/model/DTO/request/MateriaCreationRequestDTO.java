package com.mistica.EducarTransformar.model.DTO.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MateriaCreationRequestDTO {

    @NotEmpty(message = "El nombre de la materia es obligatorio")
    private String nombreMateria;

    @NotEmpty(message = "El año escolar es obligatorio")
    private String anoEscolar;

    @NotEmpty(message = "El campo turno es obligatorio")
    private String turno;

    @NotEmpty(message = "El horario de entrada es obligatorio")
    private String horarioEntrada;

    @NotEmpty(message = "El horario de salida es obligatorio")
    private String horarioSalida;
}
