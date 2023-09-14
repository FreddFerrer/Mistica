package com.mistica.EducarTransformar.controller;

import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.AlumnoDTO;
import com.mistica.EducarTransformar.model.DTO.AsistenciaDTO;
import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.DTO.UsuarioDTO;
import com.mistica.EducarTransformar.model.DTO.request.AlumnoCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.*;
import com.mistica.EducarTransformar.model.mapper.IAlumnoDTOMapper;
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
    private IAlumnoDTOMapper alumnoMapper;

    // Devuelve todos los alumnos, independientemente del rol
    @GetMapping
    public ResponseEntity<List<AlumnoDTO>> getAllAlumnos() {
        List<AlumnoDTO> alumnos = alumnoService.obtenerAlumnos();
        return ResponseEntity.ok(alumnos);
    }

    // Crea un nuevo alumno solo accesible por el rol autoridad
    @PostMapping("/nuevo")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public ResponseEntity<AlumnoDTO> agregarAlumno(@Valid @RequestBody AlumnoCreationRequestDTO alumnoDTO) {


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

        Usuario alumno = usuarioService.nuevoDocente(nuevoUsuarioAlumno);

        AlumnoDTO nuevoAlumno = alumnoService.nuevoAlumno(alumnoDTO);
        nuevoAlumno.setRol(RolUsuario.ROLE_ESTUDIANTE);
        nuevoAlumno.setUsuario(nuevoUsuarioAlumno);

        // Guardar el alumno en la base de datos
        AlumnoDTO alumnoCreado = alumnoService.guardarAlumno(nuevoAlumno);

        return ResponseEntity.ok(alumnoCreado);
    }

    // Devuelve un alumno en específico
    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDTO> getAlumno(@PathVariable Long id) {
        Optional<AlumnoDTO> alumno = alumnoService.getAlumno(id);
        return alumno.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Edita un alumno
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public ResponseEntity<AlumnoDTO> editarAlumno(
            @PathVariable Long id,
            @RequestBody AlumnoDTO alumnoDTO
    ) {
        try {
            AlumnoDTO alumnoActualizado = alumnoService.editarAlumno(id, alumnoDTO);
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
