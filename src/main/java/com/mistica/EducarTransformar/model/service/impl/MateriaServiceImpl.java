package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.MateriaDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.mapper.IMateriaDTOMapper;
import com.mistica.EducarTransformar.model.repository.IAlumnoRepository;
import com.mistica.EducarTransformar.model.repository.IMateriaRepository;
import com.mistica.EducarTransformar.model.service.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private IMateriaDTOMapper materiaMapper; // Tu mapper de Materia

    @Autowired
    private IAlumnoRepository alumnoRepository;

    @Autowired
    private IMateriaRepository materiaRepository;

    @Override
    public List<MateriaDTO> getAll() {
        List<Materia> materias = materiaRepository.findAll();
        return materias.stream()
                .map(materiaMapper::toDTO) // Convertir cada entidad en DTO
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MateriaDTO> getById(Long id) {
        Optional<Materia> materia = materiaRepository.findById(id);
        return materia.map(materiaMapper::toDTO); // Convertir la entidad en DTO si existe
    }

    @Override
    public MateriaDTO agregarMateria(MateriaCreationRequestDTO materiaDTO) {

        Materia materia = materiaMapper.toEntity(materiaDTO);
        Materia nuevaMateria = materiaRepository.save(materia);
        return materiaMapper.toDTO(nuevaMateria); // Convierte la entidad guardada en DTO
    }

    @Override
    public MateriaDTO editarMateria(Long id, MateriaDTO nuevaMateriaDTO) {
        Optional<Materia> materiaExistente = materiaRepository.findById(id);

        if (materiaExistente.isPresent()) {
            Materia materiaActualizada = materiaExistente.get();

            // Actualiza los campos de la materia con los nuevos valores del DTO
            materiaActualizada.setNombreMateria(nuevaMateriaDTO.getNombreMateria());
            //materiaActualizada.setDocente(nuevaMateriaDTO.getDocente());
            materiaActualizada.setAnoEscolar(nuevaMateriaDTO.getAnoEscolar());
            // Actualiza otros campos según sea necesario

            // Guarda la materia actualizada en la base de datos
            Materia materiaGuardada = materiaRepository.save(materiaActualizada);
            return materiaMapper.toDTO(materiaGuardada); // Convierte la entidad guardada en DTO
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

            // Asocia al alumno con la materia
            materia.getAlumnos().add(alumno);
            alumno.getMaterias().add(materia);

            // Guarda los cambios en la base de datos
            materiaRepository.save(materia);
            alumnoRepository.save(alumno);
        } else {
            // Manejo de error si la materia o el alumno no se encuentran
            throw new NotFoundException("Materia o Alumno no encontrado.");
        }
    }
}
