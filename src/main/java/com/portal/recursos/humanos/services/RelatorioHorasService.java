package com.portal.recursos.humanos.services;

import com.portal.recursos.humanos.entities.Funcionario;
import com.portal.recursos.humanos.entities.RegistroPonto;
import com.portal.recursos.humanos.exceptions.FuncionarioNaoEncontradoException;
import com.portal.recursos.humanos.repositories.FuncionarioRepository;
import com.portal.recursos.humanos.repositories.RegistroPontoRepository;
import com.portal.recursos.humanos.responses.RelatorioHorasResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
public class RelatorioHorasService {
    private final FuncionarioRepository funcionarioRepository;
    private final RegistroPontoRepository registroPontoRepository;

    public RelatorioHorasService(FuncionarioRepository funcionarioRepository, RegistroPontoRepository registroPontoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.registroPontoRepository = registroPontoRepository;
    }

    @PreAuthorize("@funcionarioSecurity.verificarPodeAlterar(#funcionarioId, authentication)")
    public RelatorioHorasResponse gerarRelatorioMensal(UUID funcionarioId, int mes, int ano) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId).orElseThrow(() ->
                        new FuncionarioNaoEncontradoException("Funcionário não encontrado com ID: " + funcionarioId));

        YearMonth periodo = YearMonth.of(ano, mes);

        LocalDate dataInicial = periodo.atDay(1);
        LocalDate dataFinal = periodo.atEndOfMonth();

        List<RegistroPonto> registros = registroPontoRepository.findByFuncionarioIdAndDataBetween(
                        funcionarioId,
                        dataInicial,
                        dataFinal);

        Duration horasTrabalhadas = registros.stream()
                .map(RegistroPonto::calcularHorasTrabalhadas)
                .reduce(Duration.ZERO, Duration::plus);

        Duration horasExtras = registros.stream()
                .map(RegistroPonto::calcularHorasExtras)
                .reduce(Duration.ZERO, Duration::plus);

        return new RelatorioHorasResponse(
                funcionario.getId(),
                funcionario.getNome(),
                mes,
                ano,
                registros.size(),
                horasTrabalhadas,
                horasExtras);
    }
}
