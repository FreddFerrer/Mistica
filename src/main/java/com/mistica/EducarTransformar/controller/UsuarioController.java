package com.mistica.EducarTransformar.controller;

import com.mistica.EducarTransformar.model.DTO.UsuarioDTO;
import com.mistica.EducarTransformar.model.DTO.request.UsuarioRegisterRequest;
import com.mistica.EducarTransformar.model.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioRegisterRequest usuario) {
        try {
            UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: Usuario con email o username ya existente.");
        }
    }
}
