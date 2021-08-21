package com.smoothie.mockitoapp.app.repositories;

import com.smoothie.mockitoapp.app.models.Examen;

import java.util.Arrays;
import java.util.List;

public class ExamenRepositoryImpl implements ExamenRepository {

    @Override
    public List<Examen> findAll() {
        return Arrays.asList(new Examen(5L, "Matemáticas"), new Examen(6L, "Lenguaje"), new Examen(7L, "Historia"));
    }

    @Override
    public Examen guardar(Examen examen) {
        return null;
    }
}