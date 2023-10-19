package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findAllByAlumnoId(Long alumnoId);
}
