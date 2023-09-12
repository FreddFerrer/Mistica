package com.mistica.EducarTransformar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @ManyToOne // Muchas materias pueden estar asociadas a un docente
    @JoinColumn(name = "docente_id")
    private Usuario docente;

    @Column(nullable = false)
    @NotEmpty(message = "campo obligatorio")
    private String anoEscolar;

    @ManyToMany(mappedBy = "materias")
    @JsonIgnore
    private List<Alumno> alumnos = new ArrayList<>();
}
