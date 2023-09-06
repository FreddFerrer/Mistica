package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.UsuarioDTO;
import com.mistica.EducarTransformar.model.DTO.request.UsuarioLoginRequest;
import com.mistica.EducarTransformar.model.DTO.request.UsuarioRegisterRequest;
import com.mistica.EducarTransformar.model.entity.Rol;
import com.mistica.EducarTransformar.model.entity.RolUsuario;
import com.mistica.EducarTransformar.model.entity.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUsuarioDTOMapper {

    UsuarioDTO toDTO (Usuario usuario);

    @InheritInverseConfiguration
    Usuario toDomain (UsuarioRegisterRequest usuario);
    Usuario toDomain (UsuarioLoginRequest usuario);

    default Rol map(String rol) {
        return Rol.builder().name(RolUsuario.valueOf(rol)).build();
    }
}
