package com.mistica.EducarTransformar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "examenes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Date fecha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "materia_id", referencedColumnName = "id")
    private Materia materia;

    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("examen")
    private List<Calificacion> calificaciones;

    public Examen(Long examenId) {
        this.id = examenId;
    }
}
