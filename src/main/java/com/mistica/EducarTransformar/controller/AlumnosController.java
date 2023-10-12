package com.mistica.EducarTransformar.controller;

import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.ListaAlumnosDTO;
import com.mistica.EducarTransformar.model.DTO.AsistenciaDTO;
import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.DTO.request.AlumnoCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.*;
import com.mistica.EducarTransformar.model.mapper.IUsuarioDTOMapper;
import com.mistica.EducarTransformar.model.service.IAlumnoService;
import com.mistica.EducarTransformar.model.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumnos")
@CrossOrigin(origins = "*")
public class AlumnosController {

    @Autowired
    private IAlumnoService alumnoService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioDTOMapper usuarioMapper;

    // Devuelve todos los alumnos, independientemente del rol
    @GetMapping
    public ResponseEntity<List<ListaAlumnosDTO>> getAllAlumnos() {
        List<ListaAlumnosDTO> alumnos = alumnoService.obtenerAlumnos();
        return ResponseEntity.ok(alumnos);
    }

    // Crea un nuevo alumno solo accesible por el rol autoridad
    @PostMapping("/nuevo")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public ResponseEntity<?> agregarAlumno(@Valid @RequestBody AlumnoCreationRequestDTO alumnoDTO) {

        if (usuarioService.existeUsuarioPorEmail(alumnoDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("El correo electrónico ya está en uso");
        }

        String contraseña = alumnoDTO.getNombre() + "alumno";
        String usernameAlumno = alumnoDTO.getNombre() + "alumno";
        String contraseñaCodificada = passwordEncoder.encode(contraseña);

        // Crear un nuevo usuario alumno
        Usuario nuevoUsuarioAlumno = new Usuario();
        nuevoUsuarioAlumno.setNombre(alumnoDTO.getNombre());
        nuevoUsuarioAlumno.setApellido(alumnoDTO.getApellido());
        nuevoUsuarioAlumno.setEmail(alumnoDTO.getEmail());
        nuevoUsuarioAlumno.setUsername(usernameAlumno);
        nuevoUsuarioAlumno.setPassword(contraseñaCodificada);
        nuevoUsuarioAlumno.setRol(RolUsuario.ROLE_ESTUDIANTE);

        // Guardar el usuario en la base de datos
        Usuario usuarioCreado = usuarioService.nuevoUsuario(nuevoUsuarioAlumno);

        // Crear un nuevo alumno y asociarlo al usuario
        Alumno nuevoAlumno = new Alumno();
        nuevoAlumno.setNombre(alumnoDTO.getNombre());
        nuevoAlumno.setApellido(alumnoDTO.getApellido());
        nuevoAlumno.setLegajo(alumnoService.obtenerUltimoNumeroLegajo() + 1);
        nuevoAlumno.setRol(RolUsuario.ROLE_ESTUDIANTE);
        nuevoAlumno.setUsuario(usuarioCreado);

        // Guardar el alumno en la base de datos
        alumnoService.guardarAlumno(nuevoAlumno);

        return ResponseEntity.ok(nuevoAlumno);
    }



    // Devuelve un alumno en específico
    @GetMapping("/{id}")
    public ResponseEntity<ListaAlumnosDTO> getAlumno(@PathVariable Long id) {
        Optional<ListaAlumnosDTO> alumno = alumnoService.getAlumno(id);
        return alumno.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Edita un alumno
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public ResponseEntity<ListaAlumnosDTO> editarAlumno(
            @PathVariable Long id,
            @RequestBody ListaAlumnosDTO listaAlumnosDTO
    ) {
        try {
            ListaAlumnosDTO alumnoActualizado = alumnoService.editarAlumno(id, listaAlumnosDTO);
            return ResponseEntity.ok(alumnoActualizado);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Borra un alumno en específico
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public ResponseEntity<?> borrarAlumno(@PathVariable Long id) {
        alumnoService.deleteAlumno(id);
        return ResponseEntity.ok("Alumno eliminado exitosamente.");
    }

    // Calificar a un alumno
    @PostMapping("/{alumnoId}/agregar-calificacion/{materiaId}")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
    public ResponseEntity<?> agregarCalificacion(
            @PathVariable Long alumnoId,
            @PathVariable Long materiaId,
            @RequestBody CalificacionDTO calificacionDTO) {
        alumnoService.establecerCalificacion(alumnoId,materiaId, calificacionDTO);
        return ResponseEntity.ok("Calificación agregada exitosamente.");
    }

    // Poner asistencia a un alumno
    @PostMapping("/{alumnoId}/agregar-asistencia/{materiaId}")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
    public ResponseEntity<?> agregarAsistencia(
            @PathVariable Long alumnoId,
            @PathVariable Long materiaId,
            @RequestBody AsistenciaDTO asistenciaDTO) {
        alumnoService.establecerAsistencia(alumnoId, materiaId, asistenciaDTO);
        return ResponseEntity.ok("Asistencia agregada exitosamente.");
    }
}
