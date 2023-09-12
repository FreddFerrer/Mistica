package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAsistenciaRepository extends JpaRepository<Asistencia, Long> {
}
