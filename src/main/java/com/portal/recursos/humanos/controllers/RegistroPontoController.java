package com.portal.recursos.humanos.controllers;

import com.portal.recursos.humanos.entities.RegistroPonto;
import com.portal.recursos.humanos.requests.RegistroPontoRequest;
import com.portal.recursos.humanos.responses.RegistroPontoResponse;
import com.portal.recursos.humanos.services.RegistroPontoService;
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
public class RegistroPontoController {
    private final RegistroPontoService registroPontoService;

    public RegistroPontoController(RegistroPontoService registroPontoService) {
        this.registroPontoService = registroPontoService;
    }

    @PostMapping("/registrar")
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

    @GetMapping("/listar")
    public ResponseEntity<Page<RegistroPontoResponse>> listarPontos(@PathVariable UUID funcionarioId,
            @PageableDefault(size = 5, sort = "data", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RegistroPontoResponse> response = registroPontoService.listarPontosPorFuncionario(funcionarioId, pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarPontos(@PathVariable UUID funcionarioId) {
        registroPontoService.deletarPontosPorFuncionario(funcionarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletar/{registroId}")
    public ResponseEntity<Void> deletarPonto(@PathVariable UUID funcionarioId, @PathVariable UUID registroId) {
        registroPontoService.deletarPonto(funcionarioId, registroId);
        return ResponseEntity.noContent().build();
    }
}
