package com.portal.recursos.humanos.responses;

import java.time.Duration;
import java.util.UUID;

public record RelatorioHorasResponse(
        UUID funcionarioId,
        String nomeFuncionario,
        int mes,
        int ano,
        long diasTrabalhados,
        Duration horasTrabalhadas,
        Duration horasExtras
) {
}
