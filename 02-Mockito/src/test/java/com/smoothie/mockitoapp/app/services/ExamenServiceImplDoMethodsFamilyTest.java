package com.smoothie.mockitoapp.app.services;

import com.smoothie.mockitoapp.app.Datos;
import com.smoothie.mockitoapp.app.models.Examen;
import com.smoothie.mockitoapp.app.repositories.ExamenRepository;
import com.smoothie.mockitoapp.app.repositories.ExamenRepositoryImpl;
import com.smoothie.mockitoapp.app.repositories.PreguntaRepository;
import com.smoothie.mockitoapp.app.repositories.PreguntasRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExamenServiceImplDoMethodsFamilyTest {

    @Mock
    private ExamenRepositoryImpl repository;

    @Mock
    private PreguntasRepositoryImpl preguntaRepository;

    @InjectMocks
    private ExamenServiceImpl service;

    @Test
    void testDoThrow() {
        Examen examen = Datos.EXAMEN;
        examen.setPreguntas(Datos.PREGUNTAS);

        doThrow(IllegalArgumentException.class).when(this.preguntaRepository).guardarVarias(anyList());

        assertThrows(IllegalArgumentException.class, () -> {
            this.service.guardar(examen);
        });
    }

    @Test
    void testDoAnswer() {
        when(this.repository.findAll()).thenReturn(Datos.EXAMENES);

        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return id == 5L ? Datos.PREGUNTAS : Collections.emptyList();
        }).when(this.preguntaRepository).findPreguntasPorExamenId(anyLong());

        Examen examen = this.service.findExamenPorNombreConPreguntas("Matemáticas");

        assertEquals(5, examen.getPreguntas().size());
        assertEquals(5L, examen.getId());
        assertEquals("Matemáticas", examen.getNombre());
        assertTrue(examen.getPreguntas().contains("Geometría"));

        verify(this.preguntaRepository).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testDoAnswerGuardarExamen() {
        // Given
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);

        doAnswer(new Answer<Examen>() {

            Long secuencia = 8L;

            @Override
            public Examen answer(InvocationOnMock invocationOnMock) throws Throwable {
                Examen examen = invocationOnMock.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
        }).when(this.repository).guardar(any(Examen.class));

        // When
        Examen examen = this.service.guardar(newExamen);

        // Then
        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("Física", examen.getNombre());
        verify(this.repository).guardar(any(Examen.class));
        verify(this.preguntaRepository).guardarVarias(anyList());
    }

    @Test
    void testDoCallRealMethod() {
        when(this.repository.findAll()).thenReturn(Datos.EXAMENES);
        doCallRealMethod().when(this.preguntaRepository).findPreguntasPorExamenId(anyLong());

        Examen examen = this.service.findExamenPorNombreConPreguntas("Matemáticas");

        assertEquals(5L, examen.getId());
        assertEquals("Matemáticas", examen.getNombre());
    }
}