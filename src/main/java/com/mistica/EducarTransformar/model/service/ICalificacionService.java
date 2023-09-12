package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;

import java.util.List;

public interface ICalificacionService {
    List<CalificacionDTO> obtenerCalificacionesPorMateria(Long materiaId);
}
