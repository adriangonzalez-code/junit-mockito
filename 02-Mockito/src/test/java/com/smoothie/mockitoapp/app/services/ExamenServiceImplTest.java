package com.smoothie.mockitoapp.app.services;

import com.smoothie.mockitoapp.app.models.Examen;
import com.smoothie.mockitoapp.app.repositories.ExamenRepository;
import com.smoothie.mockitoapp.app.repositories.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExamenServiceImplTest {

    private ExamenRepository repository;
    private PreguntaRepository preguntaRepository;
    private ExamenService service;

    @BeforeEach
    void setUp() {
        this.repository = mock(ExamenRepository.class);
        this.preguntaRepository = mock(PreguntaRepository.class);
        this.service = new ExamenServiceImpl(repository, preguntaRepository);
    }

    @Test
    void findExamenPorNombre() {

        List<Examen> datos = Arrays.asList(new Examen(5L, "Matemáticas"), new Examen(6L, "Lenguaje"), new Examen(7L, "Historia"));

        when(repository.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

        assertTrue(examen.isPresent());
        assertEquals(5L, examen.get().getId());
        assertEquals("Matemáticas", examen.get().getNombre());
    }

    @Test
    void findExamenPorNombreListaVacia() {
        List<Examen> datos = Collections.emptyList();

        when(repository.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

        assertFalse(examen.isPresent());
    }
}