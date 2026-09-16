package com.portal.recursos.humanos.controllers;

import com.portal.recursos.humanos.entities.RegistroPonto;
import com.portal.recursos.humanos.exceptions.ErrorResponse;
import com.portal.recursos.humanos.requests.RegistroPontoRequest;
import com.portal.recursos.humanos.responses.RegistroPontoResponse;
import com.portal.recursos.humanos.services.RegistroPontoService;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/funcionarios/{funcionarioId}/pontos")
@Tag(name = "Registro de Ponto", description = "Operações relacionadas ao registro e consulta de jornada dos funcionários")
@SecurityRequirement(name = "oauth2")
public class RegistroPontoController {
    private final RegistroPontoService registroPontoService;

    public RegistroPontoController(RegistroPontoService registroPontoService) {
        this.registroPontoService = registroPontoService;
    }

    @Operation(
            summary = "Registrar ponto",
            description = "Registra o ponto de um funcionário, aplicando as regras de jornada e horas extras.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de ponto realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflito com um registro de ponto existente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Funcionário não pode registrar ponto ou regra de jornada violada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PostMapping
    public ResponseEntity<RegistroPontoResponse> registrarPonto(
            @PathVariable UUID funcionarioId, @Valid @RequestBody RegistroPontoRequest request) {

        RegistroPonto registroPonto = registroPontoService.registroPonto(
                        funcionarioId,
                        request.data(),
                        request.horarioEntrada(),
                        request.horarioSaida());

        RegistroPontoResponse response = RegistroPontoResponse.fromEntity(registroPonto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Listar registros de ponto do funcionário",
            description = "Retorna os registros de ponto associados a um funcionário.",
            security = @SecurityRequirement(name = "oauth2"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registros de ponto encontrados"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @GetMapping
    public ResponseEntity<Page<RegistroPontoResponse>> listarPontos(
            @Parameter(description = "ID do funcionário", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID funcionarioId,
            @PageableDefault(size = 5, sort = "data", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RegistroPontoResponse> response = registroPontoService.listarPontosPorFuncionario(funcionarioId, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Deletar registros de ponto do funcionário",
            description = "Deleta todos os registros de ponto associados a um funcionário.",
            security = @SecurityRequirement(name = "oauth2"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registros de pontos removidos"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @DeleteMapping
    public ResponseEntity<Void> deletarPontos(
            @Parameter(description = "ID do funcionário", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID funcionarioId) {
        registroPontoService.deletarPontosPorFuncionario(funcionarioId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletar um registro de ponto do funcionário",
            description = "Deleta um registro de ponto associado a um funcionário.",
            security = @SecurityRequirement(name = "oauth2"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de ponto removido"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @DeleteMapping("/{registroId}")
    public ResponseEntity<Void> deletarPonto(
            @Parameter(description = "ID do funcionário", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID funcionarioId, @PathVariable UUID registroId) {
        registroPontoService.deletarPonto(funcionarioId, registroId);
        return ResponseEntity.noContent().build();
    }
}
