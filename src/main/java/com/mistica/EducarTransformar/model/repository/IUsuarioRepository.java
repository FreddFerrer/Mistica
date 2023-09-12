package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.RolUsuario;
import com.mistica.EducarTransformar.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<Usuario> findByRol(RolUsuario roleDocente);
    Optional<Usuario> findByIdAndRol(Long id, RolUsuario roleDocente);
}
