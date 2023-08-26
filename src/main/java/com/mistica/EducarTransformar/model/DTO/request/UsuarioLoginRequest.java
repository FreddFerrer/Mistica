package com.mistica.EducarTransformar.model.DTO.request;

import lombok.Data;

@Data
public class UsuarioLoginRequest {
    private String email;
    private String password;
}
