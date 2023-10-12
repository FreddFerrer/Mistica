package com.mistica.EducarTransformar.model.DTO.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ParcialCreationRequestDTO {
    @NotNull(message = "La fecha del parcial es obligatoria")
    private Date fechaParcial;
}
