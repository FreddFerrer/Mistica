package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.entity.Calificacion;
import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.mapper.ICalificacionDTOMapper;
import com.mistica.EducarTransformar.model.repository.ICalificacionRepository;
import com.mistica.EducarTransformar.model.repository.IMateriaRepository;
import com.mistica.EducarTransformar.model.service.ICalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalificacionServiceImpl implements ICalificacionService {

    @Autowired
    private ICalificacionRepository calificacionRepository;
    @Autowired
    private IMateriaRepository materiaRepository;
    @Autowired
    private ICalificacionDTOMapper calificacionDTOMapper;

    @Override
    public List<CalificacionDTO> obtenerCalificacionesPorMateria(Long materiaId) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new NotFoundException("Materia no encontrada"));

        // Obtén todas las calificaciones para esta materia
        List<Calificacion> calificaciones = calificacionRepository.findByMateria(materia);

        // Mapea las calificaciones, el nombre de la materia, y el nombre y apellido del alumno al DTO
        return calificaciones.stream()
                .map(calificacion -> new CalificacionDTO(
                        calificacion.getId(),
                        calificacion.getMateria().getNombreMateria(), // Obtén el nombre de la materia
                        calificacion.getCalificacion(),
                        calificacion.getFechaCalificacion(),
                        calificacion.getAlumno().getNombre(), // Obtén el nombre del alumno
                        calificacion.getAlumno().getApellido() // Obtén el apellido del alumno
                ))
                .collect(Collectors.toList());
    }
}
