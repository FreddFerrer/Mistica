package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.model.DTO.ListaAlumnosDTO;
import com.mistica.EducarTransformar.model.DTO.ListaPagosDTO;
import com.mistica.EducarTransformar.model.DTO.PagoDTO;
import com.mistica.EducarTransformar.model.entity.Alumno;
import com.mistica.EducarTransformar.model.entity.Pago;
import com.mistica.EducarTransformar.model.mapper.IAlumnoDTOMapper;
import com.mistica.EducarTransformar.model.mapper.IPagoDTOMapper;
import com.mistica.EducarTransformar.model.repository.IAlumnoRepository;
import com.mistica.EducarTransformar.model.repository.IPagoRepository;
import com.mistica.EducarTransformar.model.service.IAlumnoService;
import com.mistica.EducarTransformar.model.service.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IPagoServiceImpl implements IPagoService {

    @Autowired
    private IPagoRepository pagoRepository;
    @Autowired
    private IAlumnoRepository alumnoRepository;
    @Autowired
    private IAlumnoService alumnoService;
    @Autowired
    private IAlumnoDTOMapper alumnoMapper;
    @Autowired
    private IPagoDTOMapper pagosMapper;

    @Transactional
    public ResponseEntity<?> realizarPago(Long alumnoId, Double monto) {
        Optional<ListaAlumnosDTO> optionalAlumnoDTO = alumnoService.getAlumno(alumnoId);

        if (optionalAlumnoDTO.isPresent()) {
            ListaAlumnosDTO alumnoDTO = optionalAlumnoDTO.get();

            // Guardar la entidad Alumno si aún no está guardada
            if (alumnoDTO.getId() == null) {
                Alumno alumno = alumnoMapper.toDomain(alumnoDTO);
                alumno = alumnoRepository.save(alumno);
                // Actualiza el ID en el DTO
                alumnoDTO.setId(alumno.getId());
            }

            // Ahora puedes crear la entidad Pago con la referencia a Alumno
            Pago pago = new Pago();
            pago.setAlumno(alumnoMapper.toDomain(alumnoDTO)); // Usa el Alumno ya guardado
            pago.setFechaPago(new Date());
            pago.setMonto(monto);

            // Guardar la entidad Pago
            pago = pagoRepository.save(pago);

            // Crear un PagoDTO con la información requerida
            PagoDTO pagoDTO = new PagoDTO(
                    pago.getMonto(),
                    pago.getFechaPago()
            );

            // Realiza otras operaciones relacionadas con el estado del alumno

            return ResponseEntity.ok(pagoDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado");
        }
    }

    @Override
    public List<ListaPagosDTO> getAll() {
        List<Pago> pagos = pagoRepository.findAll();

        // Itera sobre las materias y obtén la lista de alumnos antes del mapeo
        return pagos.stream()
                .peek(pago -> {
                    Alumno alumnosDeMateria = pago.getAlumno();
                    // Realiza operaciones con la lista de alumnos si es necesario
                })
                .map(pagosMapper::toDTOList) // Convierte cada entidad en DTO
                .collect(Collectors.toList());
    }

    @Override
    public List<ListaPagosDTO> getAllPagosByAlumnoId(Long alumnoId) {
        alumnoId = alumnoRepository.findAlumnoIdByUsuarioId(alumnoId);
        List<Pago> pagos = pagoRepository.findAllByAlumnoId(alumnoId);
        System.out.println(("ID del alumno: {}"+ alumnoId));
        return pagos.stream()
                .map(pagosMapper::toDTOList)
                .collect(Collectors.toList());
    }
}
