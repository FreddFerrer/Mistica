package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.UsuarioDTO;
import com.mistica.EducarTransformar.model.DTO.request.UsuarioRegisterRequest;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> obtenerDocentes();

    Optional<Usuario> getDocente(Long id);

    Usuario nuevoDocente(Usuario docente);

    Usuario editarDocente(Long id, Usuario docente);

    void deleteDocente(Long id);

    void asignarDocenteAMateria(Long materiaId, Long docenteId);
}
