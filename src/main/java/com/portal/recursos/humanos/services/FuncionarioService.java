package com.portal.recursos.humanos.services;

import com.portal.recursos.humanos.entities.Funcionario;
import com.portal.recursos.humanos.exceptions.FuncionarioComRegistroPontoException;
import com.portal.recursos.humanos.exceptions.FuncionarioJaCadastradoException;
import com.portal.recursos.humanos.exceptions.FuncionarioNaoEncontradoException;
import com.portal.recursos.humanos.exceptions.SenhaAtualIncorretaException;
import com.portal.recursos.humanos.repositories.FuncionarioRepository;
import com.portal.recursos.humanos.repositories.RegistroPontoRepository;
import com.portal.recursos.humanos.requests.AtualizarSenhaRequest;
import com.portal.recursos.humanos.requests.FuncionarioRequest;
import com.portal.recursos.humanos.responses.FuncionarioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final RegistroPontoRepository registroPontoRepository;
    private final PasswordEncoder encoder;

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository, RegistroPontoRepository registroPontoRepository, PasswordEncoder encoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.registroPontoRepository = registroPontoRepository;
        this.encoder = encoder;
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores podem criar novos funcionários.'
    )""")
    public FuncionarioResponse registrarFuncionario(FuncionarioRequest request) {
        if (funcionarioRepository.existsByNome(request.nome()))
            throw new FuncionarioJaCadastradoException("Um funcionário com esse nome já foi criado");

        Funcionario funcionario =
                new Funcionario(request.nome(), request.cargo(), request.role(), encoder.encode(request.senha()));
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
        return FuncionarioResponse.fromEntity(funcionarioSalvo);
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores têm acesso à dados de funcionários.'
    )""")
    public Page<FuncionarioResponse> listarFuncionarios(Pageable pageable) {
        Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
        return funcionarios.map(FuncionarioResponse::fromEntity);
    }

    @PreAuthorize("@funcionarioSecurity.verificarPodeAlterar(#id, authentication)")
    public FuncionarioResponse buscarFuncionarioPorId(UUID id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário não encontrado com ID: " + id));

        return FuncionarioResponse.fromEntity(funcionario);
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores podem atualizar dados de um funcionário.'
    )""")
    public FuncionarioResponse atualizarFuncionario(UUID id, FuncionarioRequest request) {
        boolean jaExisteFuncionarioComEsseNome = funcionarioRepository.existsByNomeAndIdNot(request.nome(), id);

        if (jaExisteFuncionarioComEsseNome)
            throw new FuncionarioJaCadastradoException("Funcionários não podem ter o mesmo nome");

        Funcionario funcionarioExistente =  funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário não encontrado com ID: " + id));

        funcionarioExistente.setNome(request.nome());
        funcionarioExistente.setCargo(request.cargo());
        funcionarioExistente.setRole(request.role());
        funcionarioExistente.setSenha(encoder.encode(request.senha()));

        Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionarioExistente);

        return FuncionarioResponse.fromEntity(funcionarioAtualizado);
    }

    @PreAuthorize("@funcionarioSecurity.verificarPodeAlterar(#id, authentication)")
    public void alterarSenha(UUID id, AtualizarSenhaRequest request) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new FuncionarioNaoEncontradoException(
                                "Funcionário não encontrado com ID: " + id));

        if (!encoder.matches(request.getSenhaAtual(), funcionario.getSenha()))
            throw new SenhaAtualIncorretaException("A senha atual está incorreta.");

        funcionario.setSenha(encoder.encode(request.getNovaSenha()));

        funcionarioRepository.save(funcionario);
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores podem remover funcionários.'
    )""")
    public void deletarFuncionario(UUID id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário não encontrado com ID: " + id));

        if (registroPontoRepository.existsByFuncionarioId(id)) throw new FuncionarioComRegistroPontoException(
                "Não é possível excluir o funcionário porque existem registros de ponto associados a ele.");

        funcionarioRepository.delete(funcionario);
    }
}