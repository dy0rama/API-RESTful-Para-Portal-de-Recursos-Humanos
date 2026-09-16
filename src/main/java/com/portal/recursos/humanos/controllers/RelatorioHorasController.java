package com.portal.recursos.humanos.controllers;

import com.portal.recursos.humanos.exceptions.ErrorResponse;
import com.portal.recursos.humanos.responses.RelatorioHorasResponse;
import com.portal.recursos.humanos.services.RelatorioHorasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/funcionarios/{funcionarioId}/pontos/relatorio")
@Tag(name = "Relatório de Horas", description = "Operações relacionadas à consulta e geração de relatórios de horas trabalhadas")
@SecurityRequirement(name = "oauth2")
public class RelatorioHorasController {
    private final RelatorioHorasService relatorioHorasService;

    public RelatorioHorasController(RelatorioHorasService relatorioHorasService1) {
        this.relatorioHorasService = relatorioHorasService1;
    }

    @Operation(
            summary = "Gerar relatório de horas",
            description = "Retorna o relatório de horas trabalhadas de um funcionário.",
            security = @SecurityRequirement(name = "oauth2"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relatório de horas gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros da requisição inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @GetMapping
    public ResponseEntity<RelatorioHorasResponse> gerarRelatorioMensal(
            @Parameter(description = "ID do funcionário", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID funcionarioId,
            @Parameter(description = "Mês de Relatório", required = true, example = "9")
            @RequestParam int mes,
            @Parameter(description = "Ano de Relatório", required = true, example = "2026")
            @RequestParam int ano) {
        RelatorioHorasResponse response = relatorioHorasService.gerarRelatorioMensal(funcionarioId, mes, ano);
        return ResponseEntity.ok(response);
    }
}
