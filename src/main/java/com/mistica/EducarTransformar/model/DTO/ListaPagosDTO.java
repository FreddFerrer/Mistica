package com.mistica.EducarTransformar.model.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ListaPagosDTO {
    private Long id;
    private AlumnoDTO alumno;
    private Date fechaPago;
    private Double monto;

}
