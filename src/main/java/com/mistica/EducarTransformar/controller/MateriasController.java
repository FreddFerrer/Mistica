package com.mistica.EducarTransformar.controller;

import com.mistica.EducarTransformar.common.handler.ElementAlreadyInException;
import com.mistica.EducarTransformar.common.handler.EmptyField;
import com.mistica.EducarTransformar.common.handler.NotFoundException;
import com.mistica.EducarTransformar.model.DTO.ListaMateriasDTO;
import com.mistica.EducarTransformar.model.DTO.MateriaDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.DTO.request.ParcialCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Usuario;
import com.mistica.EducarTransformar.model.mapper.IMateriaDTOMapper;
import com.mistica.EducarTransformar.model.service.IAlumnoService;
import com.mistica.EducarTransformar.model.service.ICalificacionService;
import com.mistica.EducarTransformar.model.service.IMateriaService;
import com.mistica.EducarTransformar.model.service.IUsuarioService;
import com.mistica.EducarTransformar.security.jwt.JwtUtils;
import com.mistica.EducarTransformar.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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
    private ICalificacionService calificacionService;

    // Devuelve la lista de materias ingresando con el docente y autoridad
    @GetMapping
    @PreAuthorize("hasRole('ROLE_AUTORIDAD') or hasRole('ROLE_DOCENTE') or hasRole('ROLE_ESTUDIANTE')")
    public ResponseEntity<List<ListaMateriasDTO>> getAllMaterias() {
        List<ListaMateriasDTO> materias = materiaService.getAll();
        return ResponseEntity.ok(materias);
    }

    @GetMapping("/materiasPorDocente")
    @PreAuthorize("hasRole('ROLE_AUTORIDAD') or hasRole('ROLE_DOCENTE') or hasRole('ROLE_ESTUDIANTE')")
    public ResponseEntity<List<MateriaDTO>> getMateriasPorDocente(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long idDocente = userDetails.getId();

        // Luego, puedes usar el ID del docente para filtrar las materias, por ejemplo:
        List<MateriaDTO> materias = materiaService.getAllByDocente(idDocente);

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

        try{
            ListaMateriasDTO materiaCreada = materiaService.agregarMateria(nuevaMateriaRequestDTO);
            return ResponseEntity.ok(materiaCreada);
        }catch(EmptyField e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campos vacios");
        }

    }

    @PostMapping("/{materiaId}/agregar-alumno/{alumnoId}")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
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

    @PostMapping("/{materiaId}/parcial")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
    public ResponseEntity<String> crearParcialParaMateria(
            @PathVariable Long materiaId,
            @RequestBody @Valid ParcialCreationRequestDTO parcialDTO
    ) {
        try {
            materiaService.crearParcialParaMateria(materiaId, parcialDTO);
            return ResponseEntity.ok("Parcial creado exitosamente.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
