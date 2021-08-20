package com.smoothie.mockitoapp.app.services;

import com.smoothie.mockitoapp.app.models.Examen;

public interface ExamenService {

    Examen findExamenPorNombre(String nombre);
}