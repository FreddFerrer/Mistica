package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.ListaMateriasDTO;
import com.mistica.EducarTransformar.model.DTO.MateriaDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Materia;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IMateriaDTOMapper {

    MateriaDTO toDTO(Materia materia);

    ListaMateriasDTO toDTOList(Materia materia);

    ListaMateriasDTO toDTOWithAlumnos(Materia listaMateriasDTO, List<Alumno> alumnos);

    @InheritInverseConfiguration
    Materia toDomain(ListaMateriasDTO listaMateriasDTO);

    @Mapping(target = "id", ignore = true)
    Materia toEntity(MateriaCreationRequestDTO materiaCreateRequest);


}
