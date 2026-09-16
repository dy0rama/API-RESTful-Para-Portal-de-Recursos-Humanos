package com.portal.recursos.humanos.controllers;

import com.portal.recursos.humanos.exceptions.ErrorResponse;
import com.portal.recursos.humanos.requests.AtualizarSenhaRequest;
import com.portal.recursos.humanos.requests.FuncionarioRequest;
import com.portal.recursos.humanos.responses.FuncionarioResponse;
import com.portal.recursos.humanos.services.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Funcionários", description = "Operações relacionadas ao cadastro e gerenciamento de funcionários")
@SecurityRequirement(name = "oauth2")
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Operation(
            summary = "Registrar funcionário",
            description = "Cadastra um novo funcionário no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PostMapping
    public ResponseEntity<FuncionarioResponse> registrarFuncionario(@Valid @RequestBody FuncionarioRequest request){
        FuncionarioResponse response = funcionarioService.registrarFuncionario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Listar funcionários",
            description = "Retorna todos os funcionários cadastrados no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de funcionários retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @GetMapping
    public ResponseEntity<Page<FuncionarioResponse>> listarFuncionarios(
            @PageableDefault(size = 8, sort = "nome") Pageable pageable) {
        Page<FuncionarioResponse> response = funcionarioService.listarFuncionarios(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Buscar funcionário por ID",
            description = "Retorna os dados de um funcionário específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> buscarFuncionario(@Parameter(description = "ID do funcionário", required = true,
            example = "550e8400-e29b-41d4-a716-446655440000")@PathVariable UUID id){
        return ResponseEntity.ok(funcionarioService.buscarFuncionarioPorId(id));
    }

    @Operation(
            summary = "Atualizar funcionário",
            description = "Atualiza os dados de um funcionário existente.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Funcionário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> atualizarFuncionario(
            @PathVariable UUID id, @Valid @RequestBody FuncionarioRequest request){
        FuncionarioResponse response = funcionarioService.atualizarFuncionario(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Alterar senha do funcionário",
            description = "Altera senha de um funcionário existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Senha atual inválida",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PatchMapping("/{id}")
    public ResponseEntity<Void> alterarSenha(@PathVariable UUID id, @Valid @RequestBody AtualizarSenhaRequest request) {
        funcionarioService.alterarSenha(id, request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Excluir funcionário",
            description = "Remove um funcionário do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Funcionário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable UUID id){
        funcionarioService.deletarFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}
