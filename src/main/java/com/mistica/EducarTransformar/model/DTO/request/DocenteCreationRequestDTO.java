package com.mistica.EducarTransformar.model.DTO.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class DocenteCreationRequestDTO {
    @NotEmpty(message = "campo nombre obligatorio")
    private String nombre;

    @NotEmpty(message = "campo apellido obligatorio")
    private String apellido;

    @NotEmpty(message = "campo email obligatorio")
    @Email
    private String email;

    @NotEmpty(message = "campo username obligatorio")
    private String username;
}
