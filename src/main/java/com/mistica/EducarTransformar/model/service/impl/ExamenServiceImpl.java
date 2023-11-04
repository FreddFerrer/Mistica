package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.DTO.ExamenDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Calificacion;
import com.mistica.EducarTransformar.model.entity.Examen;
import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.mapper.IExamenDTOMapper;
import com.mistica.EducarTransformar.model.repository.IAlumnoRepository;
import com.mistica.EducarTransformar.model.repository.ICalificacionRepository;
import com.mistica.EducarTransformar.model.repository.IExamenRepository;
import com.mistica.EducarTransformar.model.repository.IMateriaRepository;
import com.mistica.EducarTransformar.model.service.IExamenService;
import com.mistica.EducarTransformar.model.service.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExamenServiceImpl implements IExamenService {

    @Autowired
    private IExamenRepository examenRepository;
    @Autowired
    private IAlumnoRepository alumnoRepository;
    @Autowired
    private IMateriaRepository materiaRepository;
    @Autowired
    private IMateriaService materiaService;
    @Autowired
    private ICalificacionRepository calificacionRepository;
    @Autowired
    private IExamenDTOMapper examenDTOMapper;


    @Override
    public Optional<Examen> obtenerById(Long materiaId) {
        Optional<Examen> examen = examenRepository.findById(materiaId);
        return examen;
    }

    @Override
    public Examen crearExamenEnMateria(String nombreExamen, Date fechaExamen, Long materiaId) {
        Materia materia = materiaService.obtenerMateriaPorId(materiaId)
                .orElseThrow(() -> new NotFoundException("Materia no encontrada"));

        if (materia != null) {
            Examen examen = new Examen();
            examen.setNombre(nombreExamen);
            examen.setFecha(fechaExamen);
            examen.setMateria(materia);

            List<Calificacion> calificaciones = new ArrayList<>();
            for (Alumno alumno : materia.getAlumnos()) {
                Calificacion calificacion = new Calificacion();
                calificacion.setAlumno(alumno);
                calificacion.setNota((double) 0);
                calificacion.setExamen(examen);
                calificaciones.add(calificacion);
            }

            examen.setCalificaciones(calificaciones);
            return examenRepository.save(examen);
        }

        return null;
    }



    @Override
    public Long obtenerIdCalificacionPorAlumnoYExamen(Long alumnoId, Long examenId) {
        Optional<Calificacion> optionalCalificacion = calificacionRepository.findByAlumnoIdAndExamenId(alumnoId, examenId);

        if (optionalCalificacion.isPresent()) {
            Calificacion calificacion = optionalCalificacion.get();
            CalificacionDTO calificacionDTO = examenDTOMapper.toCalificacionDTO(calificacion);

            return calificacionDTO.getId();
        } else {
            return null;
        }
    }

    @Override
    public Optional<CalificacionDTO> setearCalificacion(Long alumnoId, Long examenId, Double nota) {

        Long calificacionId = obtenerIdCalificacionPorAlumnoYExamen(alumnoId, examenId);

        System.out.println("id de la calificacion: " + calificacionId);

        if (calificacionId != null) {
            Optional<Calificacion> optionalCalificacion = calificacionRepository.findById(calificacionId);

            Calificacion calificacion = optionalCalificacion.get();
            calificacion.setNota(nota);

            CalificacionDTO calificacionDTO = examenDTOMapper.toCalificacionDTO(calificacion);

            ExamenDTO examenDTO = examenDTOMapper.toDTO(calificacion.getExamen());
            calificacionDTO.setExamen(examenDTO);

            calificacionRepository.save(calificacion);

            return Optional.of(calificacionDTO);
        }
        return Optional.empty();
    }



}
