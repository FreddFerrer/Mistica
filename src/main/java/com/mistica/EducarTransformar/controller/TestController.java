package com.mistica.EducarTransformar.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/padre")
    @PreAuthorize("hasRole('PADRE') or hasRole('ESTUDIANTE')")
    public String padreAccess() {
        return "Padre Content.";
    }

    @GetMapping("/personal")
    @PreAuthorize("hasRole('PERSONAL')")
    public String personalAccess() {
        return "Personal Board.";
    }

    @GetMapping("/autoridad")
    @PreAuthorize("hasRole('AUTORIDAD')")
    public String autoridadAcces() {
        return "Autoridad Board.";
    }
}
