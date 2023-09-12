package com.mistica.EducarTransformar.model.DTO.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AlumnoCreationRequestDTO {
    @NotEmpty(message = "campo nombre obligatorio")
    private String nombre;

    @NotEmpty(message = "campo apellido obligatorio")
    private String apellido;
}
