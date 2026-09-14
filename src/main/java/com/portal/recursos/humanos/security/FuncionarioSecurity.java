package com.portal.recursos.humanos.security;

import com.portal.recursos.humanos.exceptions.OperacaoNaoAutorizadaException;
import com.portal.recursos.humanos.repositories.FuncionarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FuncionarioSecurity {
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioSecurity(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void verificarPodeAlterar(UUID id, Authentication authentication) {
        boolean ehAdmin = authentication.getAuthorities().stream().anyMatch(authority ->
                "ROLE_ADMIN".equals(authority.getAuthority()));

        if (ehAdmin) return;

        boolean ehProprioFuncionario = funcionarioRepository.findById(id).map(funcionario ->
                funcionario.getNome().equalsIgnoreCase(authentication.getName())).orElse(false);

        if (!ehProprioFuncionario)
            throw new OperacaoNaoAutorizadaException("Você não pode acessar/alterar/registrar dados para outro funcionário");
    }
}
