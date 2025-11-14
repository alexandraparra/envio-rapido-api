package com.gft.envio_rapido_api.excecao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratarErrosInesperados(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR.value(), "erro", "Erro interno inesperado")
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> tratarRuntime(RuntimeException exception) {
        return ResponseEntity
                .badRequest().body(
                        Map.of("status", HttpStatus.BAD_REQUEST.value(), "erro", exception.getMessage())
                );
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, Object>> tratarRegras(RegraNegocioException exception) {
        return ResponseEntity
                .badRequest().body(
                        Map.of("status", 400, "erro", exception.getMessage())
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacoes(MethodArgumentNotValidException exception) {
        List<String> erros = exception.getBindingResult().getAllErrors().stream().map(error -> {
            if (error instanceof FieldError fieldError) {
                return fieldError.getDefaultMessage();
            }
            return error.getDefaultMessage();
        }).toList();
        return ResponseEntity.badRequest().body(Map.of("status", 400, "erros", erros));
    }

    @ExceptionHandler(LoginInvalidoException.class)
    public ResponseEntity<Map<String, Object>> tratarLogin(LoginInvalidoException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        Map.of("status", HttpStatus.UNAUTHORIZED.value(), "erro", exception.getMessage())
                );
    }

    @ExceptionHandler(UsuarioJaExisteException.class)
    public ResponseEntity<Map<String, Object>> tratarUsuarioJaExiste(UsuarioJaExisteException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        Map.of("status", 409, "erro", exception.getMessage())
                );
    }

    @ExceptionHandler(CepOrigemDestinoIgualException.class)
    public ResponseEntity<Map<String, Object>> tratarCepIgual(CepOrigemDestinoIgualException exception) {
        return ResponseEntity
                .badRequest()
                .body(
                        Map.of("status", 400, "erro", exception.getMessage())
                );
    }

    @ExceptionHandler(CepInvalidoException.class)
    public ResponseEntity<Map<String, String>> handleCepInvalido(CepInvalidoException ex) {

        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", ex.getMessage());

        return ResponseEntity.badRequest().body(resposta);
    }
}