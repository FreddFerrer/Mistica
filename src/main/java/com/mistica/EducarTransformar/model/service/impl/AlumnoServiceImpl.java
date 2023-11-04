package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.AlumnoDTO;
import com.mistica.EducarTransformar.model.DTO.ListaAlumnosDTO;
import com.mistica.EducarTransformar.model.DTO.AsistenciaDTO;
import com.mistica.EducarTransformar.model.entity.*;
import com.mistica.EducarTransformar.model.mapper.IAlumnoDTOMapper;
import com.mistica.EducarTransformar.model.repository.IAlumnoRepository;
import com.mistica.EducarTransformar.model.repository.IAsistenciaRepository;
import com.mistica.EducarTransformar.model.repository.IMateriaRepository;
import com.mistica.EducarTransformar.model.service.IAlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServiceImpl implements IAlumnoService {

    @Autowired
    private IAlumnoRepository alumnoRepository;
    @Autowired
    private IMateriaRepository materiaRepository;
    @Autowired
    private IAsistenciaRepository asistenciaRepository;
    @Autowired
    private IAlumnoDTOMapper alumnoMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ListaAlumnosDTO> obtenerAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        return alumnoMapper.toDTOs(alumnos);
    }


    @Override
    public Optional<ListaAlumnosDTO> getAlumno(Long id) {
        Optional<Alumno> alumno = alumnoRepository.findById(id);
        return  alumno.map(alumnoMapper::toDTO);
    }

    @Override
    public ListaAlumnosDTO guardarAlumno(Alumno alumno) {

        // Guarda el alumno en la base de datos
        Alumno alumnoGuardado = alumnoRepository.save(alumno);

        // Convierte la entidad Alumno de vuelta a DTO y devuelve
        return alumnoMapper.toDTO(alumnoGuardado);
    }

    @Override
    public ListaAlumnosDTO editarAlumno(Long id, ListaAlumnosDTO listaAlumnosDTO) {
        Optional<Alumno> alumnoExistente = alumnoRepository.findById(id);

        if (alumnoExistente.isPresent()) {
            Alumno alumnoActualizado = alumnoExistente.get();

            // Actualiza los campos del alumno con los nuevos valores
            alumnoActualizado.setNombre(listaAlumnosDTO.getNombre());
            alumnoActualizado.setApellido(listaAlumnosDTO.getApellido());
            // Actualiza otros campos según sea necesario

            // Guarda el alumno actualizado en la base de datos
            Alumno alumnoGuardado = alumnoRepository.save(alumnoActualizado);
            return alumnoMapper.toDTO(alumnoGuardado);
        } else {
            // Manejo de error si el alumno no se encuentra
            throw new NotFoundException("El recurso solicitado no se encuentra.");
        }
    }

    @Override
    public void deleteAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }

    @Override
    public Integer obtenerUltimoNumeroLegajo() {
        Query query = entityManager.createQuery("SELECT MAX(p.legajo) FROM Alumno p");
        Integer ultimoNumeroLegajo = (Integer) query.getSingleResult();
        return ultimoNumeroLegajo != null ? ultimoNumeroLegajo : 99; // Empezar en 100
    }

    @Override
    @Transactional
    public void establecerAsistencia(Long alumnoId, Long materiaId, AsistenciaDTO asistenciaDTO) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));

        // Aquí deberías buscar la materia por su ID y asegurarte de que exista
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new NotFoundException("Materia no encontrada"));

        Asistencia asistencia = new Asistencia();
        asistencia.setAlumno(alumno);
        asistencia.setMateria(materia); // Asignar la materia a la asistencia
        asistencia.setFechaAsistencia((Date) asistenciaDTO.getFechasInasistencia());
        asistencia.setEstado(asistenciaDTO.getEstado());

        asistenciaRepository.save(asistencia);
    }

    @Override
    public List<AlumnoDTO> obtenerAlumnosPorMateria(Long materiaId) {
        List<Alumno> alumno =  alumnoRepository.findAlumnosByMateriaId(materiaId);

        return alumnoMapper.toDTOsingular(alumno);
    }



}
