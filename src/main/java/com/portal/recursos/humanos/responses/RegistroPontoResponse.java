package com.portal.recursos.humanos.responses;

import com.portal.recursos.humanos.entities.RegistroPonto;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record RegistroPontoResponse(
        UUID id,
        UUID funcionarioId,
        String nomeFuncionario,
        LocalDate data,
        LocalTime horarioEntrada,
        LocalTime horarioSaida,
        Duration horasTrabalhadas,
        Duration horasExtras
) {

    public static RegistroPontoResponse fromEntity(
            RegistroPonto registroPonto) {return new RegistroPontoResponse(
                registroPonto.getId(),
                registroPonto.getFuncionario().getId(),
                registroPonto.getFuncionario().getNome(),
                registroPonto.getData(),
                registroPonto.getHorarioEntrada(),
                registroPonto.getHorarioSaida(),
                registroPonto.calcularHorasTrabalhadas(),
                registroPonto.calcularHorasExtras());
    }
}
