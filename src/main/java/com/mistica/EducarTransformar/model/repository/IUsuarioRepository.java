package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmailOrUsername(String email, String username);
}
