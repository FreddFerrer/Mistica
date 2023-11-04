package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ICalificacionRepository extends JpaRepository<Calificacion, Long> {

    Optional<Calificacion> findByAlumnoIdAndExamenId(Long alumnoId, Long examenId);

}
