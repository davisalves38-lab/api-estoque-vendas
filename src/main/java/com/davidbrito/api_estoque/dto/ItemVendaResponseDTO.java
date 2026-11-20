package com.davidbrito.api_estoque.dto;

import com.davidbrito.api_estoque.entity.ItemVenda;

import java.math.BigDecimal;

public record ItemVendaResponseDTO(

        Long produtoId,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subTotal
) {

    public ItemVendaResponseDTO(ItemVenda item){
        this(
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubTotal()
        );
    }

}
