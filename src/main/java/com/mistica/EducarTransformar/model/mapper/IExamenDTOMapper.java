package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.CalificacionDTO;
import com.mistica.EducarTransformar.model.DTO.ExamenDTO;
import com.mistica.EducarTransformar.model.DTO.request.ExamenCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Calificacion;
import com.mistica.EducarTransformar.model.entity.Examen;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IExamenDTOMapper {

    ExamenDTO toDTO(Examen examen);

    Examen toDomain(ExamenCreationRequestDTO examenCreationRequestDTO);

    CalificacionDTO toCalificacionDTO(Calificacion calificacion);

}
