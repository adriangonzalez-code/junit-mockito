package com.smoothiemx.sbjunitmockito.app.repositories;

import com.smoothiemx.sbjunitmockito.app.models.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepository extends JpaRepository<Banco, Long> {

}