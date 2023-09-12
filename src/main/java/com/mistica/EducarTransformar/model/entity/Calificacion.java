package com.mistica.EducarTransformar.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

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

    @ManyToOne // Muchas calificaciones pueden estar asociadas a un alumno
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne // Muchas calificaciones pueden estar asociadas a una materia
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @NotNull
    private Double calificacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_calificacion")
    private Date fechaCalificacion;

    @PrePersist
    public void prePersist() {
        fechaCalificacion = new Date();
    }
}
