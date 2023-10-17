package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.DTO.ListaAlumnosDTO;
import com.mistica.EducarTransformar.model.DTO.ListaPagosDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Pago;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IPagoService {
    ResponseEntity<?> realizarPago(Long alumnoId, Double monto);

    List<ListaPagosDTO> getAll();
}
