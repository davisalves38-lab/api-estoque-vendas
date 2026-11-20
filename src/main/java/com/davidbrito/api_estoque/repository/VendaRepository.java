package com.davidbrito.api_estoque.repository;

import com.davidbrito.api_estoque.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
