package com.mistica.EducarTransformar.model.service.impl;


import com.mistica.EducarTransformar.common.handler.ElementAlreadyInException;
import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.ListaMateriasDTO;
import com.mistica.EducarTransformar.model.DTO.MateriaDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.DTO.request.ParcialCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.entity.Parcial;
import com.mistica.EducarTransformar.model.mapper.IMateriaDTOMapper;
import com.mistica.EducarTransformar.model.repository.IAlumnoRepository;
import com.mistica.EducarTransformar.model.repository.IMateriaRepository;
import com.mistica.EducarTransformar.model.service.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private IMateriaDTOMapper materiaMapper;

    @Autowired
    private IAlumnoRepository alumnoRepository;

    @Autowired
    private IMateriaRepository materiaRepository;

    @Override
    public List<ListaMateriasDTO> getAll() {
        List<Materia> materias = materiaRepository.findAll();
        return materias.stream()
                .map(materiaMapper::toDTOList) // Convertir cada entidad en DTO
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
    public void crearParcialParaMateria(Long materiaId, ParcialCreationRequestDTO parcial) {
        Optional<Materia> materiaOptional = materiaRepository.findById(materiaId);

        if (materiaOptional.isPresent()) {
            Materia materia = materiaOptional.get();

            // Crea un nuevo parcial
            Parcial nuevoParcial = new Parcial();
            nuevoParcial.setMateria(materia);
            nuevoParcial.setFechaParcial(parcial.getFechaParcial());

            materia.getParciales().add(nuevoParcial);

            materiaRepository.save(materia);
        } else {
            throw new NotFoundException("Materia no encontrada.");
        }
    }

    @Override
    public List<MateriaDTO> getAllByDocente(Long docenteId) {
        List<Materia> materias = materiaRepository.findAllByDocenteId(docenteId);

        return materias.stream()
                .map(materiaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
