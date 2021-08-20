package com.smoothie.mockitoapp.app.repositories;

import com.smoothie.mockitoapp.app.models.Examen;

import java.util.List;

public interface ExamenRepository {

    List<Examen> findAll();
}