package com.mistica.EducarTransformar.controller;

import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.service.IMateriaService;
import com.mistica.EducarTransformar.model.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IMateriaService materiaService;

    @GetMapping("/docente/materia")
    public ResponseEntity<List<Materia>> getAllMaterias() {
        List<Materia> materias = materiaService.getAll();
        return ResponseEntity.ok(materias);
    }

    @GetMapping("/docente/materia/{id}")
    public Optional<Materia> getMateria(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Materia> materia = materiaService.getById(id);
        if (materia.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return materia;
    }

    @PostMapping("/docente/materia")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
    public ResponseEntity<Materia> agregarMateria(@Valid @RequestBody Materia nuevaMateria){
        Materia materia = materiaService.agregarMateria(nuevaMateria);
        return  ResponseEntity.ok(materia);
    }
}
