package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.AlumnoDTO;
import com.mistica.EducarTransformar.model.DTO.AsistenciaDTO;
import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.DTO.request.AlumnoCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Asistencia;
import com.mistica.EducarTransformar.model.entity.Calificacion;
import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.mapper.IAlumnoDTOMapper;
import com.mistica.EducarTransformar.model.repository.IAlumnoRepository;
import com.mistica.EducarTransformar.model.repository.IAsistenciaRepository;
import com.mistica.EducarTransformar.model.repository.ICalificacionRepository;
import com.mistica.EducarTransformar.model.repository.IMateriaRepository;
import com.mistica.EducarTransformar.model.service.IAlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
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
    private ICalificacionRepository calificacionRepository;
    @Autowired
    private IAsistenciaRepository asistenciaRepository;
    @Autowired
    private IAlumnoDTOMapper alumnoMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AlumnoDTO> obtenerAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        List<AlumnoDTO> alumnoDTOs = new ArrayList<>();

        for (Alumno alumno : alumnos) {
            AlumnoDTO alumnoDTO = alumnoMapper.toDTO(alumno);

            // Mapear las calificaciones a CalificacionDTO
            List<Calificacion> calificaciones = alumno.getCalificaciones();
            List<CalificacionDTO> calificacionDTOs = new ArrayList<>();

            for (Calificacion calificacion : calificaciones) {
                CalificacionDTO calificacionDTO = new CalificacionDTO();
                calificacionDTO.setId(calificacion.getId());

                // Verificar si la materia es nula antes de acceder a sus propiedades
                if (calificacion.getMateria() != null) {
                    calificacionDTO.setNombreMateria(calificacion.getMateria().getNombreMateria());
                } else {
                    // Si la materia es nula, puedes manejarlo de alguna manera, por ejemplo, asignar un valor predeterminado.
                    calificacionDTO.setNombreMateria("Materia Desconocida");
                }

                calificacionDTO.setCalificacion(calificacion.getCalificacion());
                calificacionDTO.setFechaCalificacion(calificacion.getFechaCalificacion());
                calificacionDTOs.add(calificacionDTO);
            }

            alumnoDTO.setCalificaciones(calificacionDTOs);
            alumnoDTOs.add(alumnoDTO);
        }

        return alumnoDTOs;
    }

    @Override
    public Optional<AlumnoDTO> getAlumno(Long id) {
        Optional<Alumno> alumno = alumnoRepository.findById(id);
        return  alumno.map(alumnoMapper::toDTO);
    }

    @Override
    public AlumnoDTO nuevoAlumno(AlumnoCreationRequestDTO alumnoDTO) {
        Alumno alumno = alumnoMapper.toDomain(alumnoDTO);
        alumno.setLegajo(obtenerUltimoNumeroLegajo() + 1);
        Alumno nuevoAlumno = alumnoRepository.save(alumno);
        return alumnoMapper.toDTO(nuevoAlumno);
    }

    @Override
    public AlumnoDTO guardarAlumno(AlumnoDTO alumnoDTO) {
        // Convierte el DTO a la entidad Alumno
        Alumno alumno = alumnoMapper.toDomain(alumnoDTO);

        // Guarda el alumno en la base de datos
        Alumno alumnoGuardado = alumnoRepository.save(alumno);

        // Convierte la entidad Alumno de vuelta a DTO y devuelve
        return alumnoMapper.toDTO(alumnoGuardado);
    }

    @Override
    public AlumnoDTO editarAlumno(Long id, AlumnoDTO alumnoDTO) {
        // Primero, verifica si el alumno existe
        Optional<Alumno> alumnoExistente = alumnoRepository.findById(id);

        if (alumnoExistente.isPresent()) {
            Alumno alumnoActualizado = alumnoExistente.get();

            // Actualiza los campos del alumno con los nuevos valores
            alumnoActualizado.setNombre(alumnoDTO.getNombre());
            alumnoActualizado.setApellido(alumnoDTO.getApellido());
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
    public void establecerCalificacion(Long alumnoId, Long materiaId, CalificacionDTO calificacionDTO) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));

        // Verificar si la materia ya existe por nombre
        Materia materia = materiaRepository.findByNombreMateria(calificacionDTO.getNombreMateria());

        if (materia == null) {
            // Si la materia no existe, lanza un error
            throw new NotFoundException("Materia no encontrada");
        }

        Calificacion calificacion = new Calificacion();
        calificacion.setAlumno(alumno);
        calificacion.setMateria(materia); // Asigna la materia encontrada o creada
        calificacion.setCalificacion(calificacionDTO.getCalificacion());
        calificacion.setFechaCalificacion(calificacionDTO.getFechaCalificacion());

        calificacionRepository.save(calificacion);
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
}
