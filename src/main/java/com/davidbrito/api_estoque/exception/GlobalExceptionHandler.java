package com.davidbrito.api_estoque.exception;

import com.davidbrito.api_estoque.dto.ErroResponseDTO;
import com.davidbrito.api_estoque.dto.ErroResponseDTO.CampoInvalido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> tratarNaoEncontrado(RecursoNaoEncontradoException ex) {
        return montarResposta(HttpStatus.NOT_FOUND, ex.getMessage(), List.of());
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponseDTO> tratarRegraNegocio(RegraNegocioException ex){
        return montarResposta(HttpStatus.BAD_REQUEST, ex.getMessage(), List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> tratarValidacao(MethodArgumentNotValidException ex){
        List<CampoInvalido> campos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> new CampoInvalido(erro.getField(), erro.getDefaultMessage()))
                .toList();
        return montarResposta(HttpStatus.BAD_REQUEST, "Existem campos inválidos na requisição", campos);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponseDTO> tratarJsonInvalido(HttpMessageNotReadableException ex){
        return montarResposta(HttpStatus.BAD_REQUEST,
                "Corpo da requisição inválido. Verifique o formato do JSON e os tipos dos campos", List.of());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponseDTO> tratarParametroInvalido(MethodArgumentTypeMismatchException ex) {
        return montarResposta(HttpStatus.BAD_REQUEST,
                "Valor inválido para o parâmetro '" + ex.getName() + "'", List.of());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErroResponseDTO> tratarRotaInexistente(NoResourceFoundException ex){
        return montarResposta(HttpStatus.NOT_FOUND, "Rota não encontrada", List.of());
    }

    private ResponseEntity<ErroResponseDTO> montarResposta(HttpStatus status, String mensagem,
                                                           List<CampoInvalido> campos) {
        ErroResponseDTO erro = new ErroResponseDTO(status.value(), mensagem, LocalDateTime.now(), campos);
        return ResponseEntity.status(status).body(erro);
    }
}
