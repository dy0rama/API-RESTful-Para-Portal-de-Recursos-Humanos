package com.portal.recursos.humanos.repositories;

import com.portal.recursos.humanos.entities.RegistroPonto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, UUID> {
    boolean existsByFuncionarioIdAndData(UUID funcionarioId, LocalDate data);
    boolean existsByFuncionarioId(UUID funcionarioId);
    void deleteByFuncionarioId(UUID funcionarioId);
    Page<RegistroPonto> findByFuncionarioId(UUID funcionarioId, Pageable pageable);
    List<RegistroPonto> findByFuncionarioIdAndDataBetween(UUID funcionarioId, LocalDate dataInicial, LocalDate dataFinal);
}
