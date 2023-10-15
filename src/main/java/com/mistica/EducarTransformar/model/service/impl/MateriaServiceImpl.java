package com.mistica.EducarTransformar.model.service.impl;


import com.mistica.EducarTransformar.common.handler.ElementAlreadyInException;
import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.DTO.ListaMateriasDTO;
import com.mistica.EducarTransformar.model.DTO.MateriaDTO;
import com.mistica.EducarTransformar.model.DTO.ParcialDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.DTO.request.ParcialCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Calificacion;
import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.entity.Parcial;
import com.mistica.EducarTransformar.model.mapper.IMateriaDTOMapper;
import com.mistica.EducarTransformar.model.mapper.IParcialDTOMapper;
import com.mistica.EducarTransformar.model.repository.IAlumnoRepository;
import com.mistica.EducarTransformar.model.repository.IMateriaRepository;
import com.mistica.EducarTransformar.model.repository.IParcialRepository;
import com.mistica.EducarTransformar.model.service.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private IMateriaDTOMapper materiaMapper;
    @Autowired
    private IParcialDTOMapper parcialMapper;

    @Autowired
    private IAlumnoRepository alumnoRepository;
    @Autowired
    private IParcialRepository parcialRepository;

    @Autowired
    private IMateriaRepository materiaRepository;

    @Override
    public List<ListaMateriasDTO> getAll() {
        List<Materia> materias = materiaRepository.findAll();

        // Itera sobre las materias y obtén la lista de alumnos antes del mapeo
        return materias.stream()
                .peek(materia -> {
                    List<Alumno> alumnosDeMateria = materia.getAlumnos();
                    // Realiza operaciones con la lista de alumnos si es necesario
                })
                .map(materiaMapper::toDTOList) // Convierte cada entidad en DTO
                .collect(Collectors.toList());
    }

    @Override
    public List<MateriaDTO> getMateriasAsignadasAlDocente(Long docenteId) {
        List<Materia> materiasAsignadas = materiaRepository.findByDocenteId(docenteId);
        return materiasAsignadas.stream()
                .map(materiaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ListaMateriasDTO> getById(Long id) {
        Optional<Materia> materia = materiaRepository.findById(id);
        return materia.map(materiaMapper::toDTOList); // Convertir la entidad en DTO si existe
    }

    @Override
    public ListaMateriasDTO agregarMateria(MateriaCreationRequestDTO materiaDTO) {

        Materia materia = materiaMapper.toEntity(materiaDTO);
        Materia nuevaMateria = materiaRepository.save(materia);
        return materiaMapper.toDTOList(nuevaMateria); // Convierte la entidad guardada en DTO
    }

    @Override
    public ListaMateriasDTO editarMateria(Long id, ListaMateriasDTO nuevaListaMateriasDTO) {
        Optional<Materia> materiaExistente = materiaRepository.findById(id);

        if (materiaExistente.isPresent()) {
            Materia materiaActualizada = materiaExistente.get();

            // Actualiza los campos de la materia con los nuevos valores del DTO
            materiaActualizada.setNombreMateria(nuevaListaMateriasDTO.getNombreMateria());
            //materiaActualizada.setDocente(nuevaMateriaDTO.getDocente());
            materiaActualizada.setAnoEscolar(nuevaListaMateriasDTO.getAnoEscolar());
            // Actualiza otros campos según sea necesario

            // Guarda la materia actualizada en la base de datos
            Materia materiaGuardada = materiaRepository.save(materiaActualizada);
            return materiaMapper.toDTOList(materiaGuardada);
        } else {
            // Manejo de error si la materia no se encuentra
            throw new NotFoundException("La materia solicitada no existe.");
        }
    }

    @Override
    public void deleteMateria(Long id) {
        materiaRepository.deleteById(id);
    }

    @Override
    public void agregarAlumnoAMateria(Long materiaId, Long alumnoId) {
        Optional<Materia> materiaOptional = materiaRepository.findById(materiaId);
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(alumnoId);

        if (materiaOptional.isPresent() && alumnoOptional.isPresent()) {
            Materia materia = materiaOptional.get();
            Alumno alumno = alumnoOptional.get();

            // Verificar si el alumno ya está asociado a la materia
            if (!materia.getAlumnos().contains(alumno)) {
                // Asocia al alumno con la materia
                materia.getAlumnos().add(alumno);
                alumno.getMaterias().add(materia);

                // Guarda los cambios en la base de datos
                materiaRepository.save(materia);
                alumnoRepository.save(alumno);
            } else {
                throw new ElementAlreadyInException("El alumno ya esta asociado a la materia");
            }
        } else {
            throw new NotFoundException("Materia o Alumno no encontrado.");
        }
    }

    @Override
    public ParcialDTO crearParcialParaMateria(Long materiaId, ParcialCreationRequestDTO parcialRequest) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new NotFoundException("Materia no encontrada"));

        Parcial parcial = new Parcial();
        parcial.setMateria(materia);
        parcial.setFechaParcial(parcialRequest.getFechaParcial());

        List<Calificacion> calificaciones = new ArrayList<>();
        for (Alumno alumno : materia.getAlumnos()) {
            Calificacion calificacion = new Calificacion();
            calificacion.setAlumno(alumno);
            calificacion.setParcial(parcial);
            calificacion.setCalificacion(null);
            calificaciones.add(calificacion);
        }

        parcial.setCalificaciones(calificaciones);

        Parcial parcialCreado = parcialRepository.save(parcial);

        return parcialMapper.toDTO(parcialCreado);
    }

    @Override
    public List<MateriaDTO> getAllByDocente(Long docenteId) {
        List<Materia> materias = materiaRepository.findAllByDocenteId(docenteId);

        return materias.stream()
                .map(materiaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void setCalificacionForAlumno(Long parcialId, Long alumnoId, CalificacionDTO calificacionDTO) {
        Parcial parcial = parcialRepository.findById(parcialId).orElseThrow(() -> new NotFoundException(parcialId.toString()));
        Alumno alumno = alumnoRepository.findById(alumnoId).orElseThrow(() -> new NotFoundException(alumnoId.toString()));

        // Asegúrate de validar que el parcial y el alumno están relacionados
        if (!contieneAlumno((List<Alumno>) parcial.getAlumno(), alumno)) {
            throw new NotFoundException("No se relacionan");
        }

        // Actualiza la calificación del alumno en el parcial
        parcial.setCalificacionForAlumno(alumno, calificacionDTO.getCalificacion());

        // Guarda el parcial actualizado en la base de datos
        parcialRepository.save(parcial);
    }

    @Override
    public void deleteParcial(Long id) {
        parcialRepository.deleteById(id);
    }

    public boolean contieneAlumno(List<Alumno> alumnos, Alumno alumno) {
        for (Alumno a : alumnos) {
            if (a.getId().equals(alumno.getId())) {
                return true;
            }
        }
        return false;
    }
}
