package com.portal.recursos.humanos.services;

import com.portal.recursos.humanos.entities.Funcionario;
import com.portal.recursos.humanos.repositories.FuncionarioRepository;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullUnmarked;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final FuncionarioRepository funcionarioRepository;

    public CustomUserDetailsService(FuncionarioRepository usuarioRepository) {
        this.funcionarioRepository = usuarioRepository;
    }

    @NullUnmarked
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado"));

        return User.builder().username(funcionario.getNome()).password(funcionario.getSenha())
                .authorities(funcionario.getRole().name()).build();
    }
}
