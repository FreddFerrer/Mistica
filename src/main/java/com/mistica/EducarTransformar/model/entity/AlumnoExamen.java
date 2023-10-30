package com.mistica.EducarTransformar.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "alumno_examenes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoExamen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "examen_id")
    private Examen examen;

    private Double calificacion;
}
