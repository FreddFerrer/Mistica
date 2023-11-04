package com.mistica.EducarTransformar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materias")
@Data
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

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Usuario docente;

    @Column(nullable = false)
    @NotEmpty(message = "campo obligatorio")
    private String anoEscolar;

    @ManyToMany(mappedBy = "materias")
    @JsonIgnore
    private List<Alumno> alumnos = new ArrayList<>();

}
