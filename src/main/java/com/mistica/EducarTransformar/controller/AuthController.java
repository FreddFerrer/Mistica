package com.mistica.EducarTransformar.controller;

import com.mistica.EducarTransformar.model.DTO.request.UsuarioLoginRequest;
import com.mistica.EducarTransformar.model.DTO.request.UsuarioRegisterRequest;
import com.mistica.EducarTransformar.model.entity.RolUsuario;
import com.mistica.EducarTransformar.model.entity.Usuario;
import com.mistica.EducarTransformar.model.repository.IUsuarioRepository;
import com.mistica.EducarTransformar.payload.JwtResponse;
import com.mistica.EducarTransformar.payload.MessageResponse;
import com.mistica.EducarTransformar.security.jwt.JwtUtils;
import com.mistica.EducarTransformar.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUsuarioRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UsuarioLoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String rol = userDetails.getRolName();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                rol));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UsuarioRegisterRequest registerRequest) {

        //comprueba si existe un email-usuario creado con el mismo nombre
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // crea un nuevo usuario
        Usuario user = new Usuario(registerRequest.getUsername(),
                registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()),
                registerRequest.getNombre(),
                registerRequest.getApellido()
                );

        // Obtén el rol del objeto Rol proporcionado en la solicitud
        RolUsuario rolUsuario = RolUsuario.valueOf(String.valueOf(registerRequest.getRol()));
        user.setRol(rolUsuario);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
