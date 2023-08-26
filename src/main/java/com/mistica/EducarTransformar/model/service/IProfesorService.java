package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.AlumnoDTO;

import java.util.List;

public interface IProfesorService {
    List<AlumnoDTO> obtenerAlumnos();
}
