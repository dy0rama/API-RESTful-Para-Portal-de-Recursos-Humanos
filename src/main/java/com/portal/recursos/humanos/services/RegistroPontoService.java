package com.portal.recursos.humanos.services;

import com.portal.recursos.humanos.entities.Funcionario;
import com.portal.recursos.humanos.entities.RegistroPonto;
import com.portal.recursos.humanos.enums.CargoFuncionario;
import com.portal.recursos.humanos.exceptions.*;
import com.portal.recursos.humanos.repositories.FuncionarioRepository;
import com.portal.recursos.humanos.repositories.RegistroPontoRepository;
import com.portal.recursos.humanos.responses.RegistroPontoResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class RegistroPontoService {
    private final RegistroPontoRepository registroPontoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public RegistroPontoService(RegistroPontoRepository registroPontoRepository, FuncionarioRepository funcionarioRepository) {
        this.registroPontoRepository = registroPontoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @PreAuthorize("@funcionarioSecurity.verificarPodeAlterar(#funcionarioId, authentication)")
    public RegistroPonto registroPonto
            (UUID funcionarioId, LocalDate data, LocalTime horarioEntrada, LocalTime horarioSaida) {

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionario não encontrado com o ID: " + funcionarioId));

        if (funcionario.getCargo() == CargoFuncionario.GERENTE || funcionario.getCargo() == CargoFuncionario.ESTAGIARIO)
            throw new FuncionarioNaoBatePontoException
                    ("Funcionários com cargo de " + funcionario.getCargo() + " não batem ponto.");

        if (horarioEntrada.isBefore(LocalTime.of(6, 0)))
            throw new HorarioInvalidoException("O horário de entrada não pode ser anterior às 06:00.");

        if (horarioSaida.isAfter(LocalTime.of(22, 0)))
            throw new HorarioInvalidoException("O horário de saída não pode ser posterior às 22:00.");

        if (!horarioSaida.isAfter(horarioEntrada))
            throw new HorarioInvalidoException("O horário de saída deve ser posterior ao horário de entrada.");

        if (registroPontoRepository.existsByFuncionarioIdAndData(funcionarioId, data))
            throw new RegistroPontoDuplicadoException("O funcionário já possui um registro de ponto para a data: " + data);

        RegistroPonto registroPonto = new RegistroPonto(data, horarioEntrada, horarioSaida, funcionario);

        validarLimiteHorasExtras(registroPonto);

        return registroPontoRepository.save(registroPonto);
    }

    private void validarLimiteHorasExtras(RegistroPonto registroPonto) {
        CargoFuncionario cargo = registroPonto.getFuncionario().getCargo();

        Duration horasExtras = registroPonto.calcularHorasExtras();

        Duration limite;

        if (cargo == CargoFuncionario.ANALISTA || cargo == CargoFuncionario.ASSISTENTE) limite = Duration.ofHours(3);
        else if (cargo == CargoFuncionario.COORDENADOR) limite = Duration.ofHours(5);
        else return;

        if (horasExtras.compareTo(limite) > 0) throw new HoraExtraExcedidaException("O funcionário com cargo de " +
                cargo + " pode realizar no máximo " + limite.toHours() + " horas extras por dia.");
    }

    @PreAuthorize("@funcionarioSecurity.verificarPodeAlterar(#funcionarioId, authentication)")
    public Page<RegistroPontoResponse> listarPontosPorFuncionario(UUID funcionarioId, Pageable pageable) {
        funcionarioRepository.findById(funcionarioId).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionário não encontrado com ID: " + funcionarioId));
        Page<RegistroPonto> registros = registroPontoRepository.findByFuncionarioId(funcionarioId, pageable);
        return registros.map(RegistroPontoResponse::fromEntity);
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores podem deletar histórico de registros de um funcionário.'
    )""")
    @Transactional
    public void deletarPontosPorFuncionario(UUID funcionarioId) {
        funcionarioRepository.findById(funcionarioId).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionário não encontrado com ID: " + funcionarioId));

        registroPontoRepository.deleteByFuncionarioId(funcionarioId);
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores podem deletar um registro de funcionário.'
    )""")
    @Transactional
    public void deletarPonto(UUID funcionarioId, UUID registroId) {
        RegistroPonto registroPonto = registroPontoRepository.findById(registroId)
                .orElseThrow(() -> new RegistroPontoNaoEncontradoException(
                                "Registro de ponto não encontrado com ID: " + registroId));

        if (!registroPonto.getFuncionario().getId().equals(funcionarioId))
            throw new RegistroPontoFuncionarioIncompativelException(
                    "O registro de ponto informado não pertence ao funcionário.");

        registroPontoRepository.delete(registroPonto);
    }
}
