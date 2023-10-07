package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.AlumnoDTO;
import com.mistica.EducarTransformar.model.DTO.AsistenciaDTO;
import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.DTO.request.AlumnoCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Asistencia;
import com.mistica.EducarTransformar.model.entity.Calificacion;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IAlumnoService {


    List<AlumnoDTO> obtenerAlumnos();

    Optional<AlumnoDTO> getAlumno(Long id);

    AlumnoDTO guardarAlumno(Alumno alumno);

    AlumnoDTO editarAlumno(Long id, AlumnoDTO alumno);

    void deleteAlumno(Long id);

    Integer obtenerUltimoNumeroLegajo();

    @Transactional
    void establecerCalificacion(Long alumnoId, Long id, CalificacionDTO calificacionDTO);

    @Transactional
    void establecerAsistencia(Long alumnoId, Long id, AsistenciaDTO asistenciaDTO);
}
