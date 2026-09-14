package com.portal.recursos.humanos.security;

import com.portal.recursos.humanos.exceptions.OperacaoNaoAutorizadaException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("administradorsecurity")
public class AdministradorSecurity {
    public boolean somenteAdmin(Authentication authentication, String mensagem) {
        boolean ehAdmin = authentication.getAuthorities().stream().anyMatch(authority ->
                "ROLE_ADMIN".equals(authority.getAuthority()));

        if (!ehAdmin) {
            throw new OperacaoNaoAutorizadaException(mensagem);
        }

        return true;
    }
}
