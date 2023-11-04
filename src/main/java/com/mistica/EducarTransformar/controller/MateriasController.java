package com.mistica.EducarTransformar.controller;

import com.mistica.EducarTransformar.common.handler.ElementAlreadyInException;
import com.mistica.EducarTransformar.common.handler.EmptyField;
import com.mistica.EducarTransformar.common.handler.InvalidHorarioException;
import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.*;
import com.mistica.EducarTransformar.model.DTO.request.ExamenCreationRequestDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.DTO.request.SetearCalificacionRequestDTO;
import com.mistica.EducarTransformar.model.entity.Calificacion;
import com.mistica.EducarTransformar.model.entity.Examen;
import com.mistica.EducarTransformar.model.mapper.IMateriaDTOMapper;
import com.mistica.EducarTransformar.model.service.*;
import com.mistica.EducarTransformar.security.jwt.JwtUtils;
import com.mistica.EducarTransformar.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/materias")
@CrossOrigin(origins = "*")
public class MateriasController {
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IAlumnoService alumnoService;
    @Autowired
    private IMateriaDTOMapper materiaDTOMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private IMateriaService materiaService;
    @Autowired
    private IExamenService examenService;
    @Autowired
    private IPagoService pagoService;

    // Devuelve la lista de materias ingresando con el docente y autoridad
    @GetMapping
    @PreAuthorize("hasRole('ROLE_AUTORIDAD') or hasRole('ROLE_DOCENTE') or hasRole('ROLE_ESTUDIANTE')")
    public ResponseEntity<List<ListaMateriasDTO>> getAllMaterias() {
        List<ListaMateriasDTO> materias = materiaService.getAll();
        return ResponseEntity.ok(materias);
    }

    @GetMapping("/materiasPorDocente")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD') or hasRole('ROLE_DOCENTE') or hasRole('ROLE_ESTUDIANTE')")
    public ResponseEntity<List<ListaMateriasDTO>> getMateriasPorDocente(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long idDocente = userDetails.getId();

        List<ListaMateriasDTO> materias = materiaService.getAllByDocente(idDocente);

        return ResponseEntity.ok(materias);
    }

    @GetMapping("/materiasPorAlumno")
    @PreAuthorize("hasRole('ROLE_ESTUDIANTE')")
    public ResponseEntity<List<MateriaDTO>> getAllMateriasByAlumno(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long alumnoId = userDetails.getId();

        List<MateriaDTO> materias = materiaService.getAllMateriasByAlumnoId(alumnoId);
        return ResponseEntity.ok(materias);
    }



    // Devuelve una materia ingresando con el docente y autoridad
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD') or hasRole('ROLE_DOCENTE')")
    public ResponseEntity<ListaMateriasDTO> getMateria(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<ListaMateriasDTO> materia = materiaService.getById(id);
        if (materia.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return ResponseEntity.ok(materia.get());
    }

    // Borra una materia (solo la autoridad)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public void borrarMateria(@PathVariable Long id) {
        materiaService.deleteMateria(id);
    }

    // Agrega una materia (solo autoridad)
    @PostMapping("/nueva")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public ResponseEntity<?> agregarMateria(@Valid @RequestBody MateriaCreationRequestDTO nuevaMateriaRequestDTO) {

        try {
            if (nuevaMateriaRequestDTO.getHorarioSalida().isBefore(nuevaMateriaRequestDTO.getHorarioEntrada())) {

                throw new InvalidHorarioException("El horario de salida debe ser posterior al horario de entrada.");
            }

            ListaMateriasDTO materiaCreada = materiaService.agregarMateria(nuevaMateriaRequestDTO);
            return ResponseEntity.ok(materiaCreada);
        } catch (EmptyField e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campos vacíos");
        } catch (InvalidHorarioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/{materiaId}/agregar-alumno/{alumnoId}")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD')")
    public ResponseEntity<?> agregarAlumnoAMateria(
            @PathVariable Long materiaId,
            @PathVariable Long alumnoId
    ) {
        try {
            materiaService.agregarAlumnoAMateria(materiaId, alumnoId);
            return ResponseEntity.ok("Alumno agregado exitosamente a la materia.");
        } catch (NotFoundException e) {
            // Manejo de la excepción si la materia o el alumno no se encuentran
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ElementAlreadyInException e) {
            // Manejo de la excepción si el alumno ya está asociado a la materia
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/nuevoExamen/{materiaId}")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
    public ResponseEntity<?> crearExamenEnMateria(
            @PathVariable Long materiaId,
            @RequestBody ExamenCreationRequestDTO examenDTO) {

        Examen examenCreado = examenService.crearExamenEnMateria(
                examenDTO.getNombre(),
                examenDTO.getFecha(),
                materiaId
        );

        if (examenCreado != null) {
            return ResponseEntity.ok("Examen creado exitosamente.");
        } else {
            return ResponseEntity.badRequest().body("No se pudo crear el examen. La materia no existe.");
        }
    }

    @GetMapping("/obtener-id/{examenId}/{alumnoId}")
    public ResponseEntity<?> obtenerIdCalificacion(
            @PathVariable Long alumnoId,
            @PathVariable Long examenId
    ) {
        Long calificacionId = examenService.obtenerIdCalificacionPorAlumnoYExamen(alumnoId, examenId);

        System.out.println(calificacionId);

        if (calificacionId != null) {
            return new ResponseEntity<>(calificacionId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/setear-calificacion/{examenId}/alumno/{alumnoId}")
    public ResponseEntity<?> calificarAlumno(
            @PathVariable Long alumnoId,
            @PathVariable Long examenId,
            @Valid @RequestBody SetearCalificacionRequestDTO nota

    ) {
        if (nota == null) {
            return new ResponseEntity<>("La calificación no puede ser nula.", HttpStatus.BAD_REQUEST);
        }

        Optional<CalificacionDTO> calificacion = examenService.setearCalificacion(alumnoId, examenId, nota.getNota());

        System.out.println(calificacion);

        if (calificacion.isPresent()) {
            return new ResponseEntity<>(calificacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo establecer la calificación.", HttpStatus.NOT_FOUND);
        }

    }



}
