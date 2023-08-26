package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.UsuarioDTO;
import com.mistica.EducarTransformar.model.DTO.request.UsuarioRegisterRequest;

public interface IUsuarioService {
    UsuarioDTO crearUsuario(UsuarioRegisterRequest usuario);
}
