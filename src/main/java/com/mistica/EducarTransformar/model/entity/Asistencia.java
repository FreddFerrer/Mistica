package com.mistica.EducarTransformar.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "asistencias")
@Getter
@Setter
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Muchas asistencias pueden estar asociadas a un alumno
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne // Muchas asistencias pueden estar asociadas a una materia
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_asistencia")
    private Date fechaAsistencia;

    @Enumerated(EnumType.STRING) // Utilizar Enum como tipo de columna
    @Column(nullable = false)
    private EstadoAsistencia estado;
}
