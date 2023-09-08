package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.entity.Materia;

import java.util.List;
import java.util.Optional;

public interface IMateriaService {
    List<Materia> getAll();
    Optional<Materia> getById(Long id);
    Materia agregarMateria(Materia materia);
}
