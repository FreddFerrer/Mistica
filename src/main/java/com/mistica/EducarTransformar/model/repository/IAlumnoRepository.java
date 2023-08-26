package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAlumnoRepository extends JpaRepository<Alumno, Long> {
}
