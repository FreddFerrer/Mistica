package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.MateriaDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Materia;

import java.util.List;
import java.util.Optional;

public interface IMateriaService {
    List<MateriaDTO> getAll();
    Optional<MateriaDTO> getById(Long id);
    MateriaDTO agregarMateria(MateriaCreationRequestDTO materia);
    MateriaDTO editarMateria(Long id, MateriaDTO materiaDTO);
    void deleteMateria(Long id);
    void agregarAlumnoAMateria(Long materiaId, Long alumnoId);
}
