package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMateriaRepository extends JpaRepository<Materia, Long> {
    Materia findByNombreMateria(String nombreMateria);

    List<Materia> findByDocenteId(Long docenteId);

    List<Materia> findAllByDocenteId(Long docenteId);
}
