package com.mistica.EducarTransformar.model.DTO;

import com.mistica.EducarTransformar.model.entity.EstadoAsistencia;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AsistenciaDTO {
    private Long id;
    private EstadoAsistencia estado;
    private List<Date> fechasInasistencia;
}
