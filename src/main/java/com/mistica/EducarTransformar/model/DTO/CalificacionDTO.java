package com.mistica.EducarTransformar.model.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class CalificacionDTO {
    private Long id;

    private String nombreMateria;
    private String nombreAlumno; // Nuevo campo para el nombre del alumno
    private String apellidoAlumno;
    private Double calificacion;
    private Date fechaCalificacion;

    public CalificacionDTO(Long id, Double calificacion, Date fechaCalificacion) {
        this.id = id;
        this.calificacion = calificacion;
        this.fechaCalificacion = fechaCalificacion;
    }

    public CalificacionDTO() {

    }

    public CalificacionDTO(Long id, String nombreMateria, Double calificacion, Date fechaCalificacion) {
        this.id = id;
        this.nombreMateria = nombreMateria;
        this.calificacion = calificacion;
        this.fechaCalificacion = fechaCalificacion;
    }
    public CalificacionDTO(Long id, String nombreMateria, Double calificacion, Date fechaCalificacion, String nombreAlumno, String apellidoAlumno) {
        this.id = id;
        this.nombreMateria = nombreMateria;
        this.calificacion = calificacion;
        this.fechaCalificacion = fechaCalificacion;
        this.nombreAlumno = nombreAlumno;
        this.apellidoAlumno = apellidoAlumno;
    }
}
