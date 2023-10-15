package com.mistica.EducarTransformar.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "calificaciones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parcial_id")
    private Parcial parcial;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    private Double calificacion;

    public Calificacion(Alumno alumno, Double calificacion) {
        this.alumno = alumno;
        this.calificacion = calificacion;
    }
}
