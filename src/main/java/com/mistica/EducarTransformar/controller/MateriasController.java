package com.mistica.EducarTransformar.controller;

import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.DTO.ListaMateriasDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.mapper.IMateriaDTOMapper;
import com.mistica.EducarTransformar.model.service.IAlumnoService;
import com.mistica.EducarTransformar.model.service.ICalificacionService;
import com.mistica.EducarTransformar.model.service.IMateriaService;
import com.mistica.EducarTransformar.model.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private IMateriaService materiaService;
    @Autowired
    private ICalificacionService calificacionService;

    // Devuelve la lista de materias ingresando con el docente y autoridad
    @GetMapping
    @PreAuthorize("hasRole('ROLE_AUTORIDAD') or hasRole('ROLE_DOCENTE') or hasRole('ROLE_ESTUDIANTE')")
    public ResponseEntity<List<ListaMateriasDTO>> getAllMaterias() {
        List<ListaMateriasDTO> materias = materiaService.getAll();
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
    public ResponseEntity<ListaMateriasDTO> agregarMateria(@Valid @RequestBody MateriaCreationRequestDTO nuevaMateriaRequestDTO) {

        // Utiliza el mapper para convertir el DTO de solicitud a la entidad Materia
        ListaMateriasDTO materiaCreada = materiaService.agregarMateria(nuevaMateriaRequestDTO);

        // Retorna la respuesta con la materia creada
        return ResponseEntity.ok(materiaCreada);
    }

    @PostMapping("/{materiaId}/agregar-alumno/{alumnoId}")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
    public ResponseEntity<String> agregarAlumnoAMateria(
            @PathVariable Long materiaId,
            @PathVariable Long alumnoId
    ) {
        try {
            materiaService.agregarAlumnoAMateria(materiaId, alumnoId);
            return ResponseEntity.ok("Alumno agregado exitosamente a la materia.");
        } catch (NotFoundException e) {
            // Manejo de la excepción si la materia o el alumno no se encuentran
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{materiaId}/calificaciones")
    public ResponseEntity<List<CalificacionDTO>> obtenerCalificacionesPorMateria(
            @PathVariable Long materiaId) {
        List<CalificacionDTO> calificaciones = calificacionService.obtenerCalificacionesPorMateria(materiaId);
        return ResponseEntity.ok(calificaciones);
    }
}
