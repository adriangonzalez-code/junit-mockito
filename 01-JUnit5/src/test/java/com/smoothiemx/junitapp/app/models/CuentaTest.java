package com.smoothiemx.junitapp.app.models;

import com.smoothiemx.junitapp.app.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

// @TestInstance(TestInstance.Lifecycle.PER_METHOD)
class CuentaTest {

    private Cuenta cuenta;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando el test");
    }

    @BeforeEach
    void initMethodTest() {
        System.out.println("Iniciando el método");
        this.cuenta = new Cuenta("John", new BigDecimal("1000.12345"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando el método");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando el test");
    }

    /**
     * Validate person name
     * If real balance is not null
     * If name is equals between expected and actual values with assertEquals
     * If name is equals to John with assertTrue
     * Adding a descriptive message to each assertion using lambda expressions
     */
    @Test
    @DisplayName("Probando nombre de la cuenta corriente")
    void testNombreCuenta() {
        String esperado = "John";
        String real = this.cuenta.getPersona();
        assertNotNull(real, () -> "La cuenta no puede ser nula");
        assertEquals(esperado, real, () -> "El nombre de la cuenta no es el que se esperaba");
        assertTrue(real.equals("John"), () -> "Nombre cuenta estada debe ser igual al real");
    }

    /**
     * Validate balance
     * If balance is not null with assertNotNull
     * If balance is equals between expected and actual values with assertEquals
     * If balance is not less to zero with assertFalse
     * If balance is higher to zero with assertTrue
     */
    @Test
    @DisplayName("Probando el saldo de la cuenta corriente, que no sea null, mayor que cero, valor esperado")
    void testSaldoCuenta() {
        assertNotNull(this.cuenta.getSaldo());
        assertEquals(1000.12345, this.cuenta.getSaldo().doubleValue());
        assertFalse(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    /**
     * Validate objects for value
     * If object2 is not equals to object1 with assertNotEquals
     * If object2 is equals to object1 with assertEquals
     */
    @Test
    @DisplayName("Testando referencias que sean iguales con el método equals")
    void testReferenciaCuenta() {
        this.cuenta = new Cuenta("John Doe", new BigDecimal("1000.12345"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("1000.12345"));

        // assertNotEquals(cuenta2, cuenta1);
        assertEquals(cuenta2, this.cuenta);
    }

    @Test
    void testDebitoCuenta() {
        this.cuenta.debito(new BigDecimal("100"));
        assertNotNull(this.cuenta.getSaldo());
        assertEquals(900, this.cuenta.getSaldo().intValue());
        assertEquals("900.12345", this.cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        this.cuenta.credito(new BigDecimal("100"));
        assertNotNull(this.cuenta.getSaldo());
        assertEquals(1100, this.cuenta.getSaldo().intValue());
        assertEquals("1100.12345", this.cuenta.getSaldo().toPlainString());
    }

    /**
     * Validate that produces any exception type with assertThrows
     */
    @Test
    void testDineroInsuficienteException() {
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            this.cuenta.debito(new BigDecimal("1500"));
        });

        String actual = exception.getMessage();
        String esperado = "Dinero insuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    void testTransferirDineroCuenta() {
        this.cuenta = new Cuenta("John Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Jane Doe", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco del estado");
        banco.transferir(cuenta2, this.cuenta, new BigDecimal("500"));

        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", this.cuenta.getSaldo().toPlainString());
    }

    @Test
    @Disabled
    void testRelacionBancoCuentas() {
        this.cuenta = new Cuenta("John", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Jane", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(this.cuenta);
        banco.addCuenta(cuenta2);
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, this.cuenta, new BigDecimal("500"));

        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", this.cuenta.getSaldo().toPlainString());
        assertEquals(2, banco.getCuentas().size());
        assertEquals("Banco del Estado", this.cuenta.getBanco().getNombre());
        assertEquals("John", banco.getCuentas().stream()
                .filter(c -> c.getPersona().equals("John"))
                .findFirst().get().getPersona());
        assertTrue(banco.getCuentas().stream()
                .anyMatch(c -> c.getPersona().equals("John")));
    }

    /**
     * Validate all assertions with assertAll, this display into the report all assertion with error
     * Adding a descriptive message to some assertions using lambda expressions
     */
    @Test
    @DisplayName("Probando relaciones entre las cuentas y el banco con assertAll")
    void testRelacionBancoCuentasAssertAll() {
        this.cuenta = new Cuenta("John", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Jane", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(this.cuenta);
        banco.addCuenta(cuenta2);
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, this.cuenta, new BigDecimal("500"));

        assertAll(
                () -> {
                    assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(), () -> "El valor del saldo de la cuenta2 no es el esperado");
                },
                () -> {
                    assertEquals("3000", this.cuenta.getSaldo().toPlainString(), () -> "El valor del saldo de la cuenta no es el esperado");
                }, () -> {
                    assertEquals(2, banco.getCuentas().size(), () -> "El banco no tiene las cuentas esperadas");
                }, () -> {
                    assertEquals("Banco del Estado", this.cuenta.getBanco().getNombre());
                }, () -> {
                    assertEquals("John", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("John"))
                            .findFirst().get().getPersona());
                }, () -> {
                    assertTrue(banco.getCuentas().stream()
                            .anyMatch(c -> c.getPersona().equals("John")));
                });
    }
}