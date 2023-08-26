package com.mistica.EducarTransformar.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "califiaciones")
@Getter
@Setter
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Muchas calificaciones pueden estar asociadas a un alumno
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne // Muchas calificaciones pueden estar asociadas a una materia
    @JoinColumn(name = "materia_id")
    private Materia materia;

    private double calificacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_calificacion")
    private Date fechaCalificacion;
}
