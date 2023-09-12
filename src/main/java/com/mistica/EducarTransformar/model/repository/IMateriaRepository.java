package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMateriaRepository extends JpaRepository<Materia, Long> {
    Materia findByNombreMateria(String nombreMateria);
}
