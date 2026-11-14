package com.davidbrito.api_estoque.dto;

import com.davidbrito.api_estoque.entity.Produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        Integer quantidadeEstoque
) {

    public ProdutoResponseDTO (Produto produto){
        this(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPrecoCusto(),
                produto.getPrecoVenda(),
                produto.getQuantidadeEstoque()
        );
    }
}
