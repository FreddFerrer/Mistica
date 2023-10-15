package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.DTO.ListaMateriasDTO;
import com.mistica.EducarTransformar.model.DTO.MateriaDTO;
import com.mistica.EducarTransformar.model.DTO.ParcialDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.DTO.request.ParcialCreationRequestDTO;
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
    void agregarAlumnoAMateria(Long materiaId, Long alumnoId);

    ParcialDTO crearParcialParaMateria(Long materiaId, ParcialCreationRequestDTO parcial);

    List<MateriaDTO> getAllByDocente(Long idDocente);

    void setCalificacionForAlumno(Long parcialId, Long alumnoId, CalificacionDTO calificacionDTO);

    void deleteParcial(Long id);
}
