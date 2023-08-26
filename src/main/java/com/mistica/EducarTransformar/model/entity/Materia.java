package com.mistica.EducarTransformar.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    private String nombreMateria;

    @ManyToOne // Muchas materias pueden estar asociadas a un docente
    @JoinColumn(name = "docente_id")
    private Usuario docente;

    @Column(nullable = false)
    private String anoEscolar;

    @ManyToMany(mappedBy = "materias")
    private List<Alumno> alumnos = new ArrayList<>();
}
