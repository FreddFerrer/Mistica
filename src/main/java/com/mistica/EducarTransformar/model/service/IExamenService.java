package com.mistica.EducarTransformar.model.service;


import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.DTO.ExamenDTO;
import com.mistica.EducarTransformar.model.entity.Calificacion;
import com.mistica.EducarTransformar.model.entity.Examen;

import java.util.Date;
import java.util.Optional;

public interface IExamenService {


    ExamenDTO crearExamenEnMateria(String nombreExamen, Long materiaId);


    Optional<Examen> obtenerById(Long materiaId);

    Examen crearExamenEnMateria(String nombreExamen, Date fechaExamen, Long materiaId);

    Long obtenerIdCalificacionPorAlumnoYExamen(Long alumnoId, Long examenId);

    Optional<CalificacionDTO> setearCalificacion(Long examenId, Long alumnoIdm, Double nota);
}
