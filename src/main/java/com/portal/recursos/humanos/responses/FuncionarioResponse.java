package com.portal.recursos.humanos.responses;

import com.portal.recursos.humanos.entities.Funcionario;
import com.portal.recursos.humanos.enums.CargoFuncionario;
import com.portal.recursos.humanos.enums.NivelDeAcesso;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public record FuncionarioResponse(
        UUID id,
        String nome,
        CargoFuncionario cargo,
        NivelDeAcesso role
){

    public static FuncionarioResponse fromEntity(Funcionario funcionario) {
        return new FuncionarioResponse(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCargo(),
                funcionario.getRole()
        );
    }
}
