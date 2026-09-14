package com.portal.recursos.humanos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FuncionarioNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> tratarFuncionarioNaoEncontrado(FuncionarioNaoEncontradoException exception) {
        return criarResposta(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(RegistroPontoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> tratarRegistroPontoDuplicado(RegistroPontoDuplicadoException exception) {
        return criarResposta(HttpStatus.ALREADY_REPORTED, exception.getMessage());
    }

    @ExceptionHandler(FuncionarioNaoBatePontoException.class)
    public ResponseEntity<ErrorResponse> tratarFuncionarioNaoBatePonto(FuncionarioNaoBatePontoException exception) {
        return criarResposta(HttpStatus.UNPROCESSABLE_CONTENT, exception.getMessage());
    }

    @ExceptionHandler(HorarioInvalidoException.class)
    public ResponseEntity<ErrorResponse> tratarHorarioInvalido(HorarioInvalidoException exception) {
        return criarResposta(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
    }

    @ExceptionHandler(HoraExtraExcedidaException.class)
    public ResponseEntity<ErrorResponse> tratarHoraExtraExcedida(HoraExtraExcedidaException exception) {
        return criarResposta(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
    }

    @ExceptionHandler(RegistroPontoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> tratarRegistroPontoNaoEncontrado(RegistroPontoNaoEncontradoException exception) {
        return criarResposta(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(RegistroPontoFuncionarioIncompativelException.class)
    public ResponseEntity<ErrorResponse> tratarRegistroPontoFuncionarioIncompativel(RegistroPontoFuncionarioIncompativelException exception) {
        return criarResposta(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
    }

    @ExceptionHandler(OperacaoNaoAutorizadaException.class)
    public ResponseEntity<ErrorResponse> tratarOperacaoNaoAutorizada(OperacaoNaoAutorizadaException exception) {
        return criarResposta(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> tratarErroDeValidacao(MethodArgumentNotValidException exception) {
        String mensagem = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Dados inválidos.");

        return criarResposta(HttpStatus.BAD_REQUEST, mensagem);
    }

    @ExceptionHandler(FuncionarioComRegistroPontoException.class)
    public ResponseEntity<ErrorResponse> tratarFuncionarioComRegistroPonto(FuncionarioComRegistroPontoException exception) {
        return criarResposta(HttpStatus.CONFLICT, exception.getMessage()
        );
    }

    @ExceptionHandler(FuncionarioJaCadastradoException.class)
    public ResponseEntity<ErrorResponse> tratarFuncionarioJaCadastrado(FuncionarioJaCadastradoException exception) {
        return criarResposta(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(SenhaAtualIncorretaException.class)
    public ResponseEntity<ErrorResponse> tratarSenhaAtualIncorreta(SenhaAtualIncorretaException exception) {
        return criarResposta(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<ErrorResponse> criarResposta(HttpStatus status, String message) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
        return ResponseEntity.status(status).body(response);
    }
}
