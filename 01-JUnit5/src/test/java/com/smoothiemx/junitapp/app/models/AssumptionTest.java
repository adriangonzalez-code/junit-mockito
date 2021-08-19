package com.smoothiemx.junitapp.app.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.*;

public class AssumptionTest {

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        this.cuenta = new Cuenta("John", new BigDecimal("1000.12345"));
    }

    @Tag("cuenta")
    @Test
    @DisplayName("Test saldo cuenta Dev")
    void testSaldoCuentaDev() {
        boolean esDev = "DEV".equals(System.getProperty("ENV"));
        assumeTrue(esDev);
        assertNotNull(this.cuenta.getSaldo());
        assertEquals(1000.12345, this.cuenta.getSaldo().doubleValue());
        assertFalse(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Tag("cuenta")
    @Test
    @DisplayName("Test saldo cuenta Dev 2")
    void testSaldoCuentaDev2() {
        boolean esDev = "DEV".equals(System.getProperty("ENV"));
        assumingThat(esDev, () -> {
            assertFalse(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
            assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        });
        assertNotNull(this.cuenta.getSaldo());
        assertEquals(1000.12345, this.cuenta.getSaldo().doubleValue());
    }
}