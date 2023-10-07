package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.AlumnoDTO;
import com.mistica.EducarTransformar.model.DTO.UsuarioDTO;
import com.mistica.EducarTransformar.model.DTO.request.UsuarioLoginRequest;
import com.mistica.EducarTransformar.model.DTO.request.UsuarioRegisterRequest;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.RolUsuario;
import com.mistica.EducarTransformar.model.entity.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUsuarioDTOMapper {

    UsuarioDTO toDTO(Usuario usuario);

    List<UsuarioDTO> toDTOs(List<Usuario> docentes);
}
