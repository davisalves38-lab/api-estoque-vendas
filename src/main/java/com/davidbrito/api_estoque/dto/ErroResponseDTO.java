package com.davidbrito.api_estoque.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponseDTO(
        int status,
        String mensagem,
        LocalDateTime dataHora,
        List<CampoInvalido> campos
) {
    public record CampoInvalido(String campo, String mensagem){
    }
}
