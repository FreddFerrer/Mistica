package com.mistica.EducarTransformar.model.DTO.request;

import lombok.Data;

import java.util.Date;

@Data
public class ExamenCreationRequestDTO {
    private String nombre;
    private Date fecha;
}
