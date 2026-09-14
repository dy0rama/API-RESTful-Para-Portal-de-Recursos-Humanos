package com.portal.recursos.humanos.controllers;

import com.portal.recursos.humanos.requests.AtualizarSenhaRequest;
import com.portal.recursos.humanos.requests.FuncionarioRequest;
import com.portal.recursos.humanos.responses.FuncionarioResponse;
import com.portal.recursos.humanos.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<FuncionarioResponse> registrarFuncionario(@Valid @RequestBody FuncionarioRequest request){
        FuncionarioResponse response = funcionarioService.registrarFuncionario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<FuncionarioResponse>> listarFuncionarios(
            @PageableDefault(size = 8, sort = "nome") Pageable pageable) {
        Page<FuncionarioResponse> response = funcionarioService.listarFuncionarios(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<FuncionarioResponse> buscarFuncionario(@PathVariable UUID id){
        return ResponseEntity.ok(funcionarioService.buscarFuncionarioPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<FuncionarioResponse> atualizarFuncionario(
            @PathVariable UUID id, @Valid @RequestBody FuncionarioRequest request){
        FuncionarioResponse response = funcionarioService.atualizarFuncionario(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/alterar-senha/{id}")
    public ResponseEntity<Void> alterarSenha(@PathVariable UUID id, @Valid @RequestBody AtualizarSenhaRequest request) {
        funcionarioService.alterarSenha(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable UUID id){
        funcionarioService.deletarFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}
