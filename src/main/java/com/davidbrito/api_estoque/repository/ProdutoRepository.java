package com.davidbrito.api_estoque.repository;

import com.davidbrito.api_estoque.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
