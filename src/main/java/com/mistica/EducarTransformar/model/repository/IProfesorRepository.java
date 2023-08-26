package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfesorRepository extends JpaRepository<Usuario, Long> {
}
