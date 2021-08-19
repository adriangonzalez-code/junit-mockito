package com.smoothiemx.junitapp.app.models;

import com.smoothiemx.junitapp.app.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Tag("dependency")
public class DependencyInjectionTest {

    private Cuenta cuenta;

    private TestInfo testInfo;
    private TestReporter testReporter;

    @BeforeEach
    void initMethodTest(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        this.testReporter.publishEntry("Ejecutando: " + this.testInfo.getDisplayName() + " " + this.testInfo.getTestMethod().orElse(null).getName() + " con las etiquetas " + this.testInfo.getTags());
        if (this.testInfo.getTags().contains("error")) {
            this.testReporter.publishEntry("Hacer algo con la etiqueta error");
        }
        this.cuenta = new Cuenta("John", new BigDecimal("1000.12345"));
    }

    @AfterEach
    void tearDown() {
        this.testReporter.publishEntry("Finalizando el método");
    }

    @Test
    @DisplayName("Probando nombre de la cuenta corriente")
    void testNombreCuenta() {
        this.testReporter.publishEntry(testInfo.getTags().toString());
        String esperado = "John";
        String real = this.cuenta.getPersona();
        assertNotNull(real, () -> "La cuenta no puede ser nula");
        assertEquals(esperado, real, () -> "El nombre de la cuenta no es el que se esperaba");
        assertTrue(real.equals("John"), () -> "Nombre cuenta estada debe ser igual al real");
    }

    @Test
    @DisplayName("Probando el saldo de la cuenta corriente, que no sea null, mayor que cero, valor esperado")
    void testSaldoCuenta() {
        assertNotNull(this.cuenta.getSaldo());
        assertEquals(1000.12345, this.cuenta.getSaldo().doubleValue());
        assertFalse(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

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

    @Tag("error")
    @Test
    void testDineroInsuficienteException() {
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            this.cuenta.debito(new BigDecimal("1500"));
        });

        String actual = exception.getMessage();
        String esperado = "Dinero insuficiente";
        assertEquals(esperado, actual);
    }

    @Tag("banco")
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

    @Tag("banco")
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

    @Tag("banco")
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

    @DisplayName("Probando Débito Cuenta Repetir")
    @RepeatedTest(value = 5, name = "{displayName} - Repetición número {currentRepetition} de {totalRepetitions}")
    void testDebitoCuentaRepeated(RepetitionInfo info) {
        if (info.getCurrentRepetition() == 3) {
            System.out.println("Estamos en la repetición " + info.getCurrentRepetition());
        }
        this.cuenta.debito(new BigDecimal("100"));
        assertNotNull(this.cuenta.getSaldo());
        assertEquals(900, this.cuenta.getSaldo().intValue());
        assertEquals("900.12345", this.cuenta.getSaldo().toPlainString());
    }
}