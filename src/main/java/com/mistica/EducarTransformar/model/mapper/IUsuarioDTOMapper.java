package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.MateriaDTO;
import com.mistica.EducarTransformar.model.DTO.UsuarioDTO;
import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.entity.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUsuarioDTOMapper {

    UsuarioDTO toDTO(Usuario usuario);

    List<UsuarioDTO> toDTOs(List<Usuario> docentes);

    MateriaDTO materiaToMateriaDTO(Materia materia);
}
