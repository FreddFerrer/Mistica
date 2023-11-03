package com.mistica.EducarTransformar.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class ListaMateriasDTO {
    private Long id;
    private String nombreMateria;
    private String turno;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime horarioEntrada;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime horarioSalida;

    private String anoEscolar;
    private DocenteDTO docente;
    private List<AlumnoDTO> alumnos;
}
