package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IComentRepository extends JpaRepository<Comentario, Long> {
}
