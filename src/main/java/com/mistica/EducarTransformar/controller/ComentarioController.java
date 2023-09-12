package com.mistica.EducarTransformar.controller;

import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Comentario;
import com.mistica.EducarTransformar.model.service.IAlumnoService;
import com.mistica.EducarTransformar.model.service.IComentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "*")
public class ComentarioController {

    @Autowired
    private IComentService comentService;

    @GetMapping
    public ResponseEntity<List<Comentario>> getAllComentarios() {
        List<Comentario> comentarios = comentService.getAllComentarios();
        return ResponseEntity.ok(comentarios);
    }

    @PostMapping
    public ResponseEntity<Comentario> publicarComentario(@RequestBody Comentario nuevoComent) {
        Comentario comentarioGuardado = comentService.nuevoComentario(nuevoComent);
        return ResponseEntity.ok(comentarioGuardado);
    }

}
