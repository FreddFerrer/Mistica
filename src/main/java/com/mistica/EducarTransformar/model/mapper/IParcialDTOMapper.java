package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.ParcialDTO;
import com.mistica.EducarTransformar.model.entity.Parcial;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IParcialDTOMapper {

    ParcialDTO toDTO (Parcial parcial);
}
