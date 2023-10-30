package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.AlumnoExamenDTO;
import com.mistica.EducarTransformar.model.DTO.ListaAlumnosDTO;
import com.mistica.EducarTransformar.model.DTO.AsistenciaDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IAlumnoService {


    List<ListaAlumnosDTO> obtenerAlumnos();

    Optional<ListaAlumnosDTO> getAlumno(Long id);

    ListaAlumnosDTO guardarAlumno(Alumno alumno);

    ListaAlumnosDTO editarAlumno(Long id, ListaAlumnosDTO alumno);

    void deleteAlumno(Long id);

    Integer obtenerUltimoNumeroLegajo();


    @Transactional
    void establecerAsistencia(Long alumnoId, Long id, AsistenciaDTO asistenciaDTO);

    List<AlumnoExamenDTO> obtenerRelacionAlumnoExamen();
}
