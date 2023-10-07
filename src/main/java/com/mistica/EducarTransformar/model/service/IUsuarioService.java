package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.UsuarioDTO;
import com.mistica.EducarTransformar.model.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<UsuarioDTO> obtenerDocentes();

    Optional<Usuario> getDocente(Long id);

    Usuario nuevoUsuario(Usuario usuario);

    Usuario editarDocente(Long id, Usuario docente);

    boolean existeUsuarioPorEmail(String email);

    void deleteDocente(Long id);

    void asignarDocenteAMateria(Long materiaId, Long docenteId);
}
