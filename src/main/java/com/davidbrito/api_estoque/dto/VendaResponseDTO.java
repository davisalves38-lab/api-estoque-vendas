package com.davidbrito.api_estoque.dto;

import com.davidbrito.api_estoque.entity.Venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendaResponseDTO (
    Long id,
    LocalDateTime dataHora,
    BigDecimal valorTotal,
    BigDecimal lucroTotal,
    List<ItemVendaResponseDTO> itens
){

    public VendaResponseDTO(Venda venda){
        this(
                venda.getId(),
                venda.getDataHora(),
                venda.getValorTotal(),
                venda.getLucroTotal(),
                venda.getItens().stream().map(ItemVendaResponseDTO::new).toList()
        );
    }

}

