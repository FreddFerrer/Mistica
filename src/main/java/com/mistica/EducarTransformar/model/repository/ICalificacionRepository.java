package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICalificacionRepository extends JpaRepository<Calificacion, Long> {

    Calificacion findByExamenIdAndAlumnoId(Long examenId, Long alumnoId);
}
