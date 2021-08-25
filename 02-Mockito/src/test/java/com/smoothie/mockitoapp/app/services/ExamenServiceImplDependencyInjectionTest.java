package com.smoothie.mockitoapp.app.services;

import com.smoothie.mockitoapp.app.Datos;
import com.smoothie.mockitoapp.app.models.Examen;
import com.smoothie.mockitoapp.app.repositories.ExamenRepository;
import com.smoothie.mockitoapp.app.repositories.PreguntaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplDependencyInjectionTest {

    @Mock
    private ExamenRepository repository;

    @Mock
    private PreguntaRepository preguntaRepository;

    @InjectMocks
    private ExamenServiceImpl service;

    /*@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }*/

    @Test
    void findExamenPorNombre() {

        when(repository.findAll()).thenReturn(Datos.EXAMENES);
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

    @Test
    void testPreguntasExamen() {
        when(this.repository.findAll()).thenReturn(Datos.EXAMENES);
        when(this.preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = this.service.findExamenPorNombreConPreguntas("Matemáticas");

        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Aritmética"));
        assertTrue(examen.getPreguntas().contains("Derivadas"));
    }

    @Test
    void testPreguntasExamenVerify() {
        when(this.repository.findAll()).thenReturn(Datos.EXAMENES);
        when(this.preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = this.service.findExamenPorNombreConPreguntas("Matemáticas");

        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Aritmética"));

        verify(this.repository).findAll();
        verify(this.preguntaRepository).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testNoExisteExamenVerify() {
        when(this.repository.findAll()).thenReturn(Collections.emptyList());
        when(this.preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = this.service.findExamenPorNombreConPreguntas("Matemáticas21");

        assertNull(examen);

        verify(this.repository).findAll();
        verify(this.preguntaRepository).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testGuardarExamen() {
        // Given
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);
        when(this.repository.guardar(any(Examen.class))).then(new Answer<Examen>() {

            Long secuencia = 8L;

            @Override
            public Examen answer(InvocationOnMock invocationOnMock) throws Throwable {
                Examen examen = invocationOnMock.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
        });

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
    void testManejoException() {
        when(this.repository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);
        when(this.preguntaRepository.findPreguntasPorExamenId(isNull())).thenThrow(IllegalArgumentException.class);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.service.findExamenPorNombreConPreguntas("Matemáticas");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());
        verify(this.repository).findAll();
        verify(this.preguntaRepository).findPreguntasPorExamenId(isNull());
    }
}