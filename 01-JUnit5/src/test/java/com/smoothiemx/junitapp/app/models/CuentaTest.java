package com.smoothiemx.junitapp.app.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("John", new BigDecimal("123456.748345"));
        /*cuenta.setPersona("John");*/
        String esperado = "John";
        String real = cuenta.getPersona();
        assertEquals(esperado, real);
        assertTrue(real.equals("John"));
    }
}