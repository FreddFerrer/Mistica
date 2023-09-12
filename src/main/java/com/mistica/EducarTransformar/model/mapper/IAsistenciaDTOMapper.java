package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.AlumnoDTO;
import com.mistica.EducarTransformar.model.DTO.AsistenciaDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Asistencia;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IAsistenciaDTOMapper {


    @Mapping(source = "alumno.id", target = "id")
    AsistenciaDTO toDTO(Asistencia asistencia);

    @InheritInverseConfiguration
    Asistencia toDomain (AsistenciaDTO asistenciaDTO);
}
