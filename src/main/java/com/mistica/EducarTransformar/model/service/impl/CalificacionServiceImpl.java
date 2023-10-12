package com.mistica.EducarTransformar.model.service.impl;

import com.mistica.EducarTransformar.model.repository.IMateriaRepository;
import com.mistica.EducarTransformar.model.service.ICalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalificacionServiceImpl implements ICalificacionService {


    @Autowired
    private IMateriaRepository materiaRepository;



}
