package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.model.entity.Materia;
import com.mistica.EducarTransformar.model.repository.IMateriaRepository;
import com.mistica.EducarTransformar.model.service.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private IMateriaRepository materiaRepository;

    @Override
    public List<Materia> getAll() {
        return materiaRepository.findAll();
    }

    @Override
    public Optional<Materia> getById(Long id) {
        return materiaRepository.findById(id);
    }

    @Override
    public Materia agregarMateria(Materia materia) {
        return materiaRepository.save(materia);
    }
}
