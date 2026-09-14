package com.portal.recursos.humanos.requests;

import com.portal.recursos.humanos.enums.CargoFuncionario;
import com.portal.recursos.humanos.enums.NivelDeAcesso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FuncionarioRequest(
        @NotBlank(message = "O nome do funcionário é obrigatório")
        String nome,

        @NotNull(message = "O cargo do funcionário é obrigatório")
        CargoFuncionario cargo,

        @NotNull(message = "A role do funcionário é obrigatória")
        NivelDeAcesso role,

        @NotBlank(message = "A senha do funcionário é obrigatória")
        @Size(min = 6, message = "A senha deve possuir pelo menos 6 caracteres")
        String senha
) {
}
