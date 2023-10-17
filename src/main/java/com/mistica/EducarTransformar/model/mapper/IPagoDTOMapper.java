package com.mistica.EducarTransformar.model.mapper;

import com.mistica.EducarTransformar.model.DTO.ListaMateriasDTO;
import com.mistica.EducarTransformar.model.DTO.ListaPagosDTO;
import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.entity.Pago;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPagoDTOMapper {

    ListaPagosDTO toDTOList(Pago pago);
}
