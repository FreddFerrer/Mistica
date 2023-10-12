package com.mistica.EducarTransformar.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "parciales")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parcial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne // Muchos parciales pueden estar asociados a una materia
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToOne // Muchos parciales pueden estar asociados a un alumno
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    private Double calificacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_parcial")
    private Date fechaParcial;

    @Column(name = "nombre_alumno")
    private String nombreAlumno;

    // Otros campos necesarios según tus requerimientos

    @PrePersist
    public void prePersist() {
        fechaParcial = new Date();
    }
}
