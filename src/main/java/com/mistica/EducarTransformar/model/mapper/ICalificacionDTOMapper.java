package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.entity.Calificacion;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICalificacionDTOMapper {

    CalificacionDTO toDTO(Calificacion calificacion);

    @InheritInverseConfiguration
    Calificacion toDomain(CalificacionDTO calificacionDTO);
}
