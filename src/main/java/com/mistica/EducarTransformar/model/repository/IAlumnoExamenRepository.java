package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.AlumnoExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAlumnoExamenRepository extends JpaRepository<AlumnoExamen, Long> {

}
