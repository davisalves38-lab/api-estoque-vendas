package com.davidbrito.api_estoque.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @Size(max = 200, message = "A descrição deve ter no máximo 255 caracteres")
        String descricao,

        @NotNull(message = "O preço de custo é obrigatório")
        @Positive(message = "O preço de custo deve ser maior que zero")
        @Digits(integer = 8, fraction = 2, message = "O preço de custo deve ter no máximo 2 casas decimais")
        BigDecimal precoCusto,

        @NotNull(message = "O preço de venda é obrigatório")
        @Positive(message = "O preço de venda deve ser maior que zero")
        @Digits(integer = 8, fraction = 2, message = "O preço de venda deve ter no máximo 2 casas decimais")
        BigDecimal precoVenda,

        @NotNull(message = "A quantidade em estoque é obrigatóriad")
        @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa")
        Integer quantidadeEstoque
) {
}
