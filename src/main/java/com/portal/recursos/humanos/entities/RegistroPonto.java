package com.portal.recursos.humanos.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "registros_ponto")
public class RegistroPonto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate data;

    private LocalTime horarioEntrada;

    private LocalTime horarioSaida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    public RegistroPonto() {}

    public RegistroPonto(LocalDate data, LocalTime horarioEntrada, LocalTime horarioSaida, Funcionario funcionario) {
        this.data = data;
        this.horarioEntrada = horarioEntrada;
        this.horarioSaida = horarioSaida;
        this.funcionario = funcionario;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public LocalTime getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(LocalTime horaSaida) {
        this.horarioSaida = horaSaida;
    }

    public LocalTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(LocalTime horaEntrada) {
        this.horarioEntrada = horaEntrada;
    }

    public Duration calcularHorasTrabalhadas() {
        Duration tempoTotal = Duration.between(horarioEntrada, horarioSaida);
        return tempoTotal.minusHours(1);
    }

    public Duration calcularHorasExtras() {
        Duration horasTrabalhadas = calcularHorasTrabalhadas();

        Duration jornadaPadrao = Duration.ofHours(8);

        if (horasTrabalhadas.compareTo(jornadaPadrao) <= 0) {
            return Duration.ZERO;
        }

        return horasTrabalhadas.minus(jornadaPadrao);
    }
}
