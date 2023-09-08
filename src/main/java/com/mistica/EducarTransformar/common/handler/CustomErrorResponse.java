package com.mistica.EducarTransformar.common.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomErrorResponse {
    private HttpStatus estado;
    private String mensaje;

    public CustomErrorResponse(HttpStatus estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }
}
