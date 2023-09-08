package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.model.service.IAlumnoService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class AlumnoServiceImpl implements IAlumnoService {



    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Integer obtenerUltimoNumeroPedido() {
        Query query = entityManager.createQuery("SELECT MAX(p.nroPedido) FROM Pedidos p");
        Integer ultimoNumeroPedido = (Integer) query.getSingleResult();
        return ultimoNumeroPedido != null ? ultimoNumeroPedido : 99; // Empezar en 100
    }
}
