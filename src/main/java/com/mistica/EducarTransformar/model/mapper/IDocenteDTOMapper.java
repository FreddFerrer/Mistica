package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.DocenteDTO;
import com.mistica.EducarTransformar.model.DTO.ListaDocentesDTO;
import com.mistica.EducarTransformar.model.DTO.MateriaDTO;
import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDocenteDTOMapper {

    DocenteDTO toDTO(Usuario usuario);
    List<ListaDocentesDTO> toDTOList(List<Usuario> docentes);
    ListaDocentesDTO toListaDTO(Usuario usuario);
    List<MateriaDTO> toMateriaDTOList(List<Materia> materias);
}
