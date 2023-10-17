package com.mistica.EducarTransformar.model.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class PagoDTO {
    private Double monto;
    private Date fechaPago;

    public PagoDTO(Double monto, Date fechaPago) {

        this.monto = monto;
        this.fechaPago = fechaPago;
    }
}
