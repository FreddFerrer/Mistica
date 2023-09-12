package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.MateriaDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Materia;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IMateriaDTOMapper {

    @Mapping(target = "alumnos", source = "alumnos")
    MateriaDTO toDTO(Materia materia);

    @InheritInverseConfiguration
    Materia toDomain(MateriaDTO materiaDTO);

    // Agrega un nuevo método para mapear desde el DTO de solicitud a la entidad Materia
    @Mapping(target = "id", ignore = true) // Ignora el id, ya que es autogenerado
    @Mapping(target = "nombreMateria", source = "nombreMateria")
    @Mapping(target = "anoEscolar", source = "anoEscolar")
    Materia toEntity(MateriaCreationRequestDTO materiaCreateRequest);

    // Agrega otro método para mapear desde el DTO de solicitud a la entidad Materia
    @InheritInverseConfiguration
    MateriaCreationRequestDTO toRequestDTO(Materia materia);
}
