package com.smoothie.mockitoapp.app.services;

import com.smoothie.mockitoapp.app.Datos;
import com.smoothie.mockitoapp.app.repositories.ExamenRepository;
import com.smoothie.mockitoapp.app.repositories.PreguntaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExamenServiceImplArgumentMatchersTest {

    @Mock
    private ExamenRepository repository;

    @Mock
    private PreguntaRepository preguntaRepository;

    @InjectMocks
    private ExamenServiceImpl service;

    @Test
    void testArgumentMatchers() {
        when(this.repository.findAll()).thenReturn(Datos.EXAMENES);
        when(this.preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        this.service.findExamenPorNombreConPreguntas("Matemáticas");

        verify(this.repository).findAll();
        verify(this.preguntaRepository).findPreguntasPorExamenId(argThat(arg -> arg != null && arg >= 5L));
    }

    @Test
    void testArgumentMatchers2() {
        when(this.repository.findAll()).thenReturn(Datos.EXAMENES);
        when(this.preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        this.service.findExamenPorNombreConPreguntas("Matemáticas");

        verify(this.repository).findAll();
        verify(this.preguntaRepository).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));
    }

    public static class MiArgsMatchers implements ArgumentMatcher<Long> {

        private Long argument;

        @Override
        public boolean matches(Long argument) {
            this.argument = argument;
            return this.argument != null && this.argument > 0;
        }

        @Override
        public String toString() {
            return "Es para un mensaje personalizado de error que imprime mockito en caso de que falle el test. "+ argument +" debe ser un entero positivo";
        }
    }

    @Test
    void testArgumentMatchers3() {
        when(this.repository.findAll()).thenReturn(Datos.EXAMENES);
        when(this.preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        this.service.findExamenPorNombreConPreguntas("Matemáticas");

        verify(this.repository).findAll();
        verify(this.preguntaRepository).findPreguntasPorExamenId(argThat((argument) -> argument != null && argument > 0));
    }
}