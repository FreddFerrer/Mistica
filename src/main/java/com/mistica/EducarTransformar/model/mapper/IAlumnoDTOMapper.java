package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.AlumnoDTO;
import com.mistica.EducarTransformar.model.DTO.request.AlumnoCreationRequestDTO;
import com.mistica.EducarTransformar.model.DTO.request.MateriaCreationRequestDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Materia;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAlumnoDTOMapper {
    AlumnoDTO toDTO(Alumno alumno);
    List<AlumnoDTO> toDTOs(List<Alumno> alumnos);

    @InheritInverseConfiguration
    Alumno toDomain(AlumnoCreationRequestDTO alumnoDTO);
    Alumno toDomain(AlumnoDTO alumnoDTO);
}
