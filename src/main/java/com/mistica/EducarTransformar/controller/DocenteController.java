package com.mistica.EducarTransformar.controller;

import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.DocenteDTO;
import com.mistica.EducarTransformar.model.DTO.ListaDocentesDTO;
import com.mistica.EducarTransformar.model.DTO.UsuarioDTO;
import com.mistica.EducarTransformar.model.DTO.request.DocenteCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.RolUsuario;
import com.mistica.EducarTransformar.model.entity.Usuario;
import com.mistica.EducarTransformar.model.service.IAlumnoService;
import com.mistica.EducarTransformar.model.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/docentes")
@CrossOrigin(origins = "*")
public class DocenteController {

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IAlumnoService alumnoService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public ResponseEntity<List<ListaDocentesDTO>> getAllDocentes() {
        List<ListaDocentesDTO> docentes = usuarioService.obtenerDocentes();
        return ResponseEntity.ok(docentes);
    }

    //devuelve un docente ingresando solo con autoridad
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public Optional<Usuario> getDocente(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Usuario> docente = usuarioService.getDocente(id);
        if (docente.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return docente;
    }

    //borra un docente (solo la autoridad)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public void borrarDocente(@PathVariable Long id){
        usuarioService.deleteDocente(id);
    }

    //agrega un docente (solo autoridad)
    @PostMapping("/nuevo")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public ResponseEntity<Usuario> agregarDocente(@Valid @RequestBody DocenteCreationRequestDTO docenteRequest) {
        // Generar la contraseña automáticamente
        String usernameDocente = docenteRequest.getNombre() + "docente";
        String contraseña = docenteRequest.getNombre() + "docente";

        String contraseñaCodificada = passwordEncoder.encode(contraseña);

        // Crear un nuevo usuario (docente)
        Usuario nuevoDocente = new Usuario();
        nuevoDocente.setNombre(docenteRequest.getNombre());
        nuevoDocente.setApellido(docenteRequest.getApellido());
        nuevoDocente.setEmail(docenteRequest.getEmail());
        nuevoDocente.setUsername(usernameDocente);
        nuevoDocente.setPassword(contraseñaCodificada);
        nuevoDocente.setRol(RolUsuario.ROLE_DOCENTE);

        Usuario docente = usuarioService.nuevoUsuario(nuevoDocente);

        return ResponseEntity.ok(docente);
    }

    //asignar un docente a una materia
    @PostMapping("/{materiaId}/agregar-docente/{docenteId}")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public ResponseEntity<?> asignarDocenteAMateria(
            @PathVariable Long materiaId,
            @PathVariable Long docenteId
    ) {
        try {
            usuarioService.asignarDocenteAMateria(materiaId, docenteId);
            return ResponseEntity.ok("Docente agregado exitosamente a la materia.");
        } catch (NotFoundException e) {
            // Manejo de la excepción si la materia o el alumno no se encuentran
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
