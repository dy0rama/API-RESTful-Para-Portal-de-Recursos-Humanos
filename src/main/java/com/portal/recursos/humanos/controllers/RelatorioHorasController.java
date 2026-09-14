package com.portal.recursos.humanos.controllers;

import com.portal.recursos.humanos.responses.RelatorioHorasResponse;
import com.portal.recursos.humanos.services.RelatorioHorasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/funcionarios/{funcionarioId}/pontos")
public class RelatorioHorasController {
    private final RelatorioHorasService relatorioHorasService;

    public RelatorioHorasController(RelatorioHorasService relatorioHorasService1) {
        this.relatorioHorasService = relatorioHorasService1;
    }

    @GetMapping("/relatorio")
    public ResponseEntity<RelatorioHorasResponse> gerarRelatorioMensal(
            @PathVariable UUID funcionarioId, @RequestParam int mes, @RequestParam int ano) {
        RelatorioHorasResponse response = relatorioHorasService.gerarRelatorioMensal(funcionarioId, mes, ano);
        return ResponseEntity.ok(response);
    }
}
