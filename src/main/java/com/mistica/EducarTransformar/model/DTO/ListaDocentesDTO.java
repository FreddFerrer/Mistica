package com.mistica.EducarTransformar.model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ListaDocentesDTO {
    private Long id;
    private String username;
    private String email;
    private String nombre;
    private String apellido;
    private List<MateriaDTO> materiaACargo;
}
