package com.davidbrito.api_estoque.controller;

import com.davidbrito.api_estoque.dto.CategoriaRequestDTO;
import com.davidbrito.api_estoque.dto.CategoriaResponseDTO;
import com.davidbrito.api_estoque.repository.ProdutoRepository;
import com.davidbrito.api_estoque.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service){ this.service = service; }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@Valid @RequestBody CategoriaRequestDTO dados,
                                                      UriComponentsBuilder uriBuilder){
        CategoriaResponseDTO categoria = service.criar(dados);
        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.id()).toUri();
         return ResponseEntity.created(uri).body(categoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id,
                                                          @Valid @RequestBody CategoriaRequestDTO dados){
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
