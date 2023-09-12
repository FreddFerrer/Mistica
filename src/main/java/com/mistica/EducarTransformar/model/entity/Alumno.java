package com.mistica.EducarTransformar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alumnos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer legajo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RolUsuario rol;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("alumno")
    private List<Calificacion> calificaciones = new ArrayList<>();


    @JsonIgnore@OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("alumno")
    private List<Asistencia> asistencias = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "alumno_materia",
            joinColumns = @JoinColumn(name = "alumno_id"),
            inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    @JsonIgnore
    private List<Materia> materias = new ArrayList<>();
}
