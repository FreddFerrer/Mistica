package com.mistica.EducarTransformar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ManyToOne // Un parcial pertenece a una materia
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    private Double calificacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_parcial")
    private Date fechaParcial;

    @Column(name = "nombre_alumno")
    private String nombreAlumno;

    @OneToMany(mappedBy = "parcial", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("parcial")
    private List<Calificacion> calificaciones = new ArrayList<>();

    public Calificacion getCalificacionForAlumno(Alumno alumno) {
        for (Calificacion calificacion : calificaciones) {
            if (calificacion.getAlumno().equals(alumno)) {
                return calificacion;
            }
        }
        return null;
    }

    public void setCalificacionForAlumno(Alumno alumno, Double calificacion) {
        Calificacion calificacionExistente = this.getCalificacionForAlumno(alumno);
        if (calificacionExistente != null) {
            calificacionExistente.setCalificacion(calificacion);
        } else {
            // Si la calificación no existe, puedes crear una nueva Calificacion
            // y agregarla a la colección de calificaciones del parcial.
            Calificacion nuevaCalificacion = new Calificacion(alumno, calificacion);
            this.getCalificaciones().add(nuevaCalificacion);
        }
    }

}
