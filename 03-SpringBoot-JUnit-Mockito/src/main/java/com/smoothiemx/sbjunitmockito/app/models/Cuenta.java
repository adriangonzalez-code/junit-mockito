package com.smoothiemx.sbjunitmockito.app.models;

import com.smoothiemx.sbjunitmockito.app.exceptions.DineroInsuficienteException;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String persona;
    private BigDecimal saldo;

    public Cuenta() {
    }

    public Cuenta(Long id, String persona, BigDecimal saldo) {
        this.id = id;
        this.persona = persona;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void debido(BigDecimal monto) {
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new DineroInsuficienteException("Dinero insuficiente en la cuenta.");
        }

        this.saldo = nuevoSaldo;
    }

    public void credito(BigDecimal monto) {
        this.saldo = this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuenta)) return false;

        Cuenta cuenta = (Cuenta) o;

        if (getId() != null ? !getId().equals(cuenta.getId()) : cuenta.getId() != null) return false;
        if (getPersona() != null ? !getPersona().equals(cuenta.getPersona()) : cuenta.getPersona() != null)
            return false;
        return getSaldo() != null ? getSaldo().equals(cuenta.getSaldo()) : cuenta.getSaldo() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getPersona() != null ? getPersona().hashCode() : 0);
        result = 31 * result + (getSaldo() != null ? getSaldo().hashCode() : 0);
        return result;
    }
}
