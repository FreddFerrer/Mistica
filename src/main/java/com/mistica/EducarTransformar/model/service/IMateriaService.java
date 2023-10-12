package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.ListaMateriasDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;

import java.util.List;
import java.util.Optional;

public interface IMateriaService {
    List<ListaMateriasDTO> getAll();
    Optional<ListaMateriasDTO> getById(Long id);
    ListaMateriasDTO agregarMateria(MateriaCreationRequestDTO materia);
    ListaMateriasDTO editarMateria(Long id, ListaMateriasDTO listaMateriasDTO);
    void deleteMateria(Long id);
    void agregarAlumnoAMateria(Long materiaId, Long alumnoId);
}
