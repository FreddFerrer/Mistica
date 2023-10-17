package com.mistica.EducarTransformar.model.DTO;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class MateriaDTO {
    private String nombreMateria;
    private String turno;
    private LocalTime horarioEntrada;
    private LocalTime horarioSalida;
    private String anoEscolar;
    private DocenteDTO docente;
    //private List<AlumnoDTO> alumnos;
    private List<ParcialDTO> parciales;
}
