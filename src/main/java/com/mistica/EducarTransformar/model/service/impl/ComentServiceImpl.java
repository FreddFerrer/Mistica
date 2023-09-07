package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.model.entity.Comentario;
import com.mistica.EducarTransformar.model.repository.IComentRepository;
import com.mistica.EducarTransformar.model.service.IComentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentServiceImpl implements IComentService {

    @Autowired
    private IComentRepository comentRepository;

    @Override
    public List<Comentario> getAllComentarios() {
        return comentRepository.findAll();
    }

    @Override
    public Comentario nuevoComentario(Comentario nuevoComent) {
        return comentRepository.save(nuevoComent);
    }
}
