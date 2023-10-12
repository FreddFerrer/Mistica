package com.mistica.EducarTransformar.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "campo obligatorio")
    private String username;

    @Column(nullable = false)
    @NotEmpty(message = "campo obligatorio")
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    @NotEmpty(message = "campo obligatorio")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RolUsuario rol;

    @Column(nullable = false)
    @NotEmpty(message = "campo obligatorio")
    private String nombre;

    @OneToMany(mappedBy = "docente", fetch = FetchType.LAZY)
    private List<Materia> materiaACargo;

    @Column(nullable = false)
    @NotEmpty(message = "campo obligatorio")
    private String apellido;

    public Usuario(String username, String password, String email, RolUsuario rol, String nombre, String apellido) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Usuario(String username, String email, String password, String nombre, String apellido) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
    }
}
