package com.smoothiemx.sbjunitmockito.app.services;

import com.smoothiemx.sbjunitmockito.app.models.Banco;
import com.smoothiemx.sbjunitmockito.app.models.Cuenta;
import com.smoothiemx.sbjunitmockito.app.repositories.BancoRepository;
import com.smoothiemx.sbjunitmockito.app.repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private BancoRepository bancoRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    public Cuenta findById(Long id) {
        return this.cuentaRepository.findById(id).orElseThrow(null);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco =this.bancoRepository.findById(bancoId).orElseThrow(null);
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = this.cuentaRepository.findById(cuentaId).orElseThrow(null);
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {
        Cuenta cuentaOrigen = this.cuentaRepository.findById(numCuentaOrigen).orElseThrow(null);
        cuentaOrigen.debido(monto);
        this.cuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino = this.cuentaRepository.findById(numCuentaDestino).orElseThrow(null);
        cuentaDestino.credito(monto);
        this.cuentaRepository.save(cuentaDestino);

        Banco banco = this.bancoRepository.findById(bancoId).orElseThrow(null);
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        this.bancoRepository.save(banco);
    }
}