package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExamenRepository extends JpaRepository<Examen, Long> {
}
