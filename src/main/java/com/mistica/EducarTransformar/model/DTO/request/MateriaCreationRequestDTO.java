package com.mistica.EducarTransformar.model.DTO.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MateriaCreationRequestDTO {

    @NotEmpty(message = "El nombre de la materia es obligatorio")
    private String nombreMateria;

    @NotEmpty(message = "El año escolar es obligatorio")
    private String anoEscolar;
}
