package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.UsuarioDTO;
import com.mistica.EducarTransformar.model.DTO.request.UsuarioRegisterRequest;
import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.entity.RolUsuario;
import com.mistica.EducarTransformar.model.entity.Usuario;
import com.mistica.EducarTransformar.model.mapper.IUsuarioDTOMapper;
import com.mistica.EducarTransformar.model.repository.IMateriaRepository;
import com.mistica.EducarTransformar.model.repository.IUsuarioRepository;
import com.mistica.EducarTransformar.model.service.IMateriaService;
import com.mistica.EducarTransformar.model.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IMateriaRepository materiaRepository;


    @Override
    public List<Usuario> obtenerDocentes() {
        return usuarioRepository.findByRol(RolUsuario.ROLE_DOCENTE);
    }

    @Override
    public Optional<Usuario> getDocente(Long id) {
        return usuarioRepository.findByIdAndRol(id, RolUsuario.ROLE_DOCENTE);
    }

    @Override
    public Usuario nuevoDocente(Usuario docente) {
        return usuarioRepository.save(docente);
    }

    @Override
    public Usuario editarDocente(Long id, Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario usuarioActualizado = usuarioExistente.get();

            // Actualiza los campos del usuario con los nuevos valores
            usuarioActualizado.setUsername(usuario.getUsername());
            usuarioActualizado.setPassword(usuario.getPassword());
            usuarioActualizado.setEmail(usuario.getEmail());
            usuarioActualizado.setRol(usuario.getRol());
            usuarioActualizado.setNombre(usuario.getNombre());
            usuarioActualizado.setApellido(usuario.getApellido());
            // Actualiza otros campos según sea necesario

            // Guarda el usuario actualizado en la base de datos
            return usuarioRepository.save(usuarioActualizado);
        } else {
            // Manejo de error si el usuario no se encuentra
            throw new NotFoundException("El usuario solicitado no existe.");
        }
    }

    @Override
    public void deleteDocente(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public void asignarDocenteAMateria(Long materiaId, Long docenteId) {
        // Obtener la materia y el docente de la base de datos
        Optional<Materia> materiaExistente = materiaRepository.findById(materiaId);
        Optional<Usuario> docenteExistente = usuarioRepository.findById(docenteId);

        if (materiaExistente.isPresent() && docenteExistente.isPresent()) {
            Materia materia = materiaExistente.get();
            Usuario docente = docenteExistente.get();

            // Asignar el docente a la materia
            materia.setDocente(docente);

            // Guardar la materia actualizada en la base de datos
            materiaRepository.save(materia);
        } else {
            // Manejo de error si la materia o el docente no se encuentran
            throw new NotFoundException("La materia o el docente solicitado no existen.");
        }
    }
}
