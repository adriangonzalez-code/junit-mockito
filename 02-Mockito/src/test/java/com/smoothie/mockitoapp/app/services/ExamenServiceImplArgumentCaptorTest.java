package com.smoothie.mockitoapp.app.services;

import com.smoothie.mockitoapp.app.Datos;
import com.smoothie.mockitoapp.app.repositories.ExamenRepository;
import com.smoothie.mockitoapp.app.repositories.PreguntaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExamenServiceImplArgumentCaptorTest {

    @Mock
    private ExamenRepository repository;

    @Mock
    private PreguntaRepository preguntaRepository;

    @InjectMocks
    private ExamenServiceImpl service;

    @Captor
    ArgumentCaptor<Long> captor;

    @Test
    void testArgumentCaptor() {
        when(this.repository.findAll()).thenReturn(Datos.EXAMENES);
        this.service.findExamenPorNombreConPreguntas("Matemáticas");

        verify(this.preguntaRepository).findPreguntasPorExamenId(captor.capture());

        assertEquals(5L, captor.getValue());
    }
}