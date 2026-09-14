package com.portal.recursos.humanos.requests;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record RegistroPontoRequest(
        @NotNull(message = "A data do ponto é obrigatória")
        LocalDate data,

        @NotNull(message = "O horário de entrada é obrigatório")
        LocalTime horarioEntrada,

        @NotNull(message = "O horário de saída é obrigatório")
        LocalTime horarioSaida
) {
}
