package com.mistica.EducarTransformar.model.DTO;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class ListaMateriasDTO {
    private Long id;
    private String nombreMateria;
    private String turno;
    private LocalTime horarioEntrada;
    private LocalTime horarioSalida;
    private String anoEscolar;
    private List<AlumnoDTO> alumnos;
    private List<ParcialDTO> parciales;
    private DocenteDTO docente;
}
