package com.mistica.EducarTransformar.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "comentarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "campo obligatorio")
    private String nombre;

    @NotEmpty(message = "campo obligatorio")
    @Column(nullable = false, length = 1000) // Ajusta la longitud según tus necesidades
    private String contenido;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "fecha_comentario")
    private Date fechaComentario;

    @PrePersist
    public void prePersist() {
        fechaComentario = new Date();
    }
}
