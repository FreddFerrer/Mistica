package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.model.DTO.UsuarioDTO;
import com.mistica.EducarTransformar.model.DTO.request.UsuarioRegisterRequest;
import com.mistica.EducarTransformar.model.entity.Usuario;
import com.mistica.EducarTransformar.model.mapper.IUsuarioDTOMapper;
import com.mistica.EducarTransformar.model.repository.IUsuarioRepository;
import com.mistica.EducarTransformar.model.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IUsuarioDTOMapper usuarioMapper;

    @Override
    public UsuarioDTO crearUsuario(UsuarioRegisterRequest usuario) {

        return  null;
    }
}
