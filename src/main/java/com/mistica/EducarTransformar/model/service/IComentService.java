package com.mistica.EducarTransformar.model.service;

import com.mistica.EducarTransformar.model.entity.Comentario;

import java.util.List;

public interface IComentService {
    List<Comentario> getAllComentarios();
    Comentario nuevoComentario(Comentario nuevoComent);
}
