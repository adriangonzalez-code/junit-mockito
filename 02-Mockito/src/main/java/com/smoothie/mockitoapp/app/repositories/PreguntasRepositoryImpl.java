package com.smoothie.mockitoapp.app.repositories;

import com.smoothie.mockitoapp.app.Datos;

import java.util.List;

public class PreguntasRepositoryImpl implements PreguntaRepository {

    @Override
    public List<String> findPreguntasPorExamenId(Long id) {
        System.out.println("PreguntasRepositoryImpl.findPreguntasPorExamenId");
        return Datos.PREGUNTAS;
    }

    @Override
    public void guardarVarias(List<String> preguntas) {
        System.out.println("PreguntasRepositoryImpl.guardarVarias");
    }
}
