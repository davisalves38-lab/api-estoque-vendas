package com.davidbrito.api_estoque.dto;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        String descricao,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        Integer quantidadeEstoque
) {
}
