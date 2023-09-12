package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Calificacion;
import com.mistica.EducarTransformar.model.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByMateriaId(Long materiaId);

    List<Calificacion> findByMateria(Materia materia);
}
