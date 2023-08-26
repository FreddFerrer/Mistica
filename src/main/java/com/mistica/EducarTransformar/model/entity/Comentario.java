package com.mistica.EducarTransformar.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false, length = 1000) // Ajusta la longitud según tus necesidades
    private String contenido;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_comentario")
    private Date fechaComentario;
}
