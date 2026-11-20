package com.davidbrito.api_estoque.controller;

import com.davidbrito.api_estoque.dto.VendaRequestDTO;
import com.davidbrito.api_estoque.dto.VendaResponseDTO;
import com.davidbrito.api_estoque.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService service;

    public VendaController(VendaService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VendaResponseDTO> registrar(@Valid @RequestBody VendaRequestDTO dados,
                                                      UriComponentsBuilder uriBuilder){
        VendaResponseDTO venda = service.registrar(dados);
        URI uri = uriBuilder.path("/vendas/{id}").buildAndExpand(venda.id()).toUri();
        return ResponseEntity.created(uri).body(venda);
    }

    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

}
