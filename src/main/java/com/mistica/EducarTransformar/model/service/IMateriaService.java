package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.ListaMateriasDTO;
import com.mistica.EducarTransformar.model.DTO.MateriaDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Materia;

import java.util.List;
import java.util.Optional;

public interface IMateriaService {
    List<ListaMateriasDTO> getAll();

    List<MateriaDTO> getMateriasAsignadasAlDocente(Long docenteId);

    Optional<ListaMateriasDTO> getById(Long id);

    ListaMateriasDTO agregarMateria(MateriaCreationRequestDTO materia);
    ListaMateriasDTO editarMateria(Long id, ListaMateriasDTO listaMateriasDTO);
    void deleteMateria(Long id);
    MateriaDTO agregarAlumnoAMateria(Long materiaId, Long alumnoId);

    List<ListaMateriasDTO> getAllByDocente(Long idDocente);

    List<MateriaDTO> getAllMateriasByAlumnoId(Long alumnoId);

    Optional<Materia> obtenerMateriaPorId(Long materiaId);

}
