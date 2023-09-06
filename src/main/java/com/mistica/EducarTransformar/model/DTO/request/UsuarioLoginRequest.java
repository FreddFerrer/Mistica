package com.mistica.EducarTransformar.model.DTO.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UsuarioLoginRequest {


    private String username;

    private String password;
}
