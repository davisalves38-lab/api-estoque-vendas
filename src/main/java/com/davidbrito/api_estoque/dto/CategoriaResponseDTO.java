package com.davidbrito.api_estoque.dto;

import com.davidbrito.api_estoque.entity.Categoria;

public record CategoriaResponseDTO(Long id, String nome) {

    public CategoriaResponseDTO(Categoria categoria){
        this(categoria.getId(), categoria.getNome());
    }
}
