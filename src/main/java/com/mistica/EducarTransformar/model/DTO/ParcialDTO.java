package com.mistica.EducarTransformar.model.DTO;

import com.mistica.EducarTransformar.model.entity.Calificacion;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ParcialDTO {
    private Date fechaParcial;
    private List<CalificacionDTO> calificaciones;
}
