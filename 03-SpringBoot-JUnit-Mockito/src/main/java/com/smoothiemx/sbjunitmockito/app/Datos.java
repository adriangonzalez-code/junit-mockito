package com.smoothiemx.sbjunitmockito.app;

import com.smoothiemx.sbjunitmockito.app.models.Banco;
import com.smoothiemx.sbjunitmockito.app.models.Cuenta;

import java.math.BigDecimal;

public class Datos {

    public static Cuenta crearCuenta001() {
        return new Cuenta(1L, "Adrián", new BigDecimal("1000"));
    }

    public static Cuenta crearCuenta002() {
        return new Cuenta(2L, "Jhon", new BigDecimal("2000"));
    }

    public static Banco banco() {
        return new Banco(1L, "El banco financiero", 0);
    }
}