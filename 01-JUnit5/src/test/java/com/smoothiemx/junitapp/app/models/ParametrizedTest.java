package com.smoothiemx.junitapp.app.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParametrizedTest {

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        this.cuenta = new Cuenta("John", new BigDecimal("1000.12345"));
    }

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @ValueSource(strings = {"100", "200", "300", "500", "700", "1000"})
    void testDebitoCuentaValueSource(String monto) {
        this.cuenta.debito(new BigDecimal(monto));
        assertNotNull(this.cuenta.getSaldo());
        assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @CsvSource({"1,100", "2,200", "3,300", "4,500", "5,700", "6,1000"})
    void testDebitoCuentaCsvSource(String indice, String monto) {
        System.out.println(indice + "->" + monto);
        this.cuenta.debito(new BigDecimal(monto));
        assertNotNull(this.cuenta.getSaldo());
        assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @CsvFileSource(resources = "/data.csv")
    void testDebitoCuentaCsvFileSource(String monto) {
        this.cuenta.debito(new BigDecimal(monto));
        assertNotNull(this.cuenta.getSaldo());
        assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @MethodSource("montoList")
    void testDebitoCuentaMethodSource(String monto) {
        this.cuenta.debito(new BigDecimal(monto));
        assertNotNull(this.cuenta.getSaldo());
        assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    private static List<String> montoList() {
        return Arrays.asList("100", "200", "300", "500", "700", "1000");
    }

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @CsvSource({"200,100,John,Adrian", "250,200,Pepe,Pepe", "300,300,maria,Maria", "500,500,Lucas,Luca,", "750,700,Pepe,Pepa", "1000.12345,1000.12345,Cata,Cata"})
    void testDebitoCuentaCsvSource2(String saldo, String monto, String esperado, String actual) {
        System.out.println(saldo + "->" + monto);

        this.cuenta.setSaldo(new BigDecimal(saldo));
        this.cuenta.debito(new BigDecimal(monto));
        this.cuenta.setPersona(actual);

        assertNotNull(this.cuenta.getSaldo());
        assertNotNull(this.cuenta.getPersona());
        assertEquals(esperado, this.cuenta.getPersona());
        assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @CsvFileSource(resources = "/data2.csv")
    void testDebitoCuentaCsvFileSource2(String saldo, String monto, String esperado, String actual) {
        this.cuenta.setSaldo(new BigDecimal(saldo));
        this.cuenta.debito(new BigDecimal(monto));
        this.cuenta.setPersona(actual);

        assertNotNull(this.cuenta.getSaldo());
        assertNotNull(this.cuenta.getPersona());
        assertEquals(esperado, this.cuenta.getPersona());
        assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }
}