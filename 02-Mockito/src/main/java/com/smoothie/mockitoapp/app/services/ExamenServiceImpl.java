package com.smoothie.mockitoapp.app.services;

import com.smoothie.mockitoapp.app.models.Examen;
import com.smoothie.mockitoapp.app.repositories.ExamenRepository;

import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {

    private ExamenRepository examenRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    @Override
    public Examen findExamenPorNombre(String nombre) {
        Optional<Examen> examenOptional = this.examenRepository.findAll().stream().filter(e -> e.getNombre().contains(nombre)).findFirst();
        Examen examen = null;
        if (examenOptional.isPresent()) {
            examen = examenOptional.get();
        }

        return examen;
    }
}