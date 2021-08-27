package com.smoothiemx.sbjunitmockito.app.repositories;

import com.smoothiemx.sbjunitmockito.app.models.Banco;

import java.util.List;

public interface BancoRepository {

    List<Banco> findAll();

    Banco findById(Long id);

    void update(Banco banco);
}