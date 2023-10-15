package com.mistica.EducarTransformar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materias")
@Getter
@Setter
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "campo obligatorio")
    private String nombreMateria;

    @Column(nullable = false)
    private String turno;

    @Column(nullable = false)
    private LocalTime horarioEntrada;

    @Column(nullable = false)
    private LocalTime horarioSalida;

    @ManyToOne // Muchas materias pueden estar asociadas a un docente
    @JoinColumn(name = "docente_id")
    private Usuario docente;

    @Column(nullable = false)
    @NotEmpty(message = "campo obligatorio")
    private String anoEscolar;

    @ManyToMany(mappedBy = "materias")
    @JsonIgnore
    private List<Alumno> alumnos = new ArrayList<>();

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("materia")
    private List<Parcial> parciales = new ArrayList<>();
}
