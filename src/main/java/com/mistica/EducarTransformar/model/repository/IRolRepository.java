package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Rol;
import com.mistica.EducarTransformar.model.entity.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByName(RolUsuario name);
}
