package com.davidbrito.api_estoque.service;


import com.davidbrito.api_estoque.dto.CategoriaRequestDTO;
import com.davidbrito.api_estoque.dto.CategoriaResponseDTO;
import com.davidbrito.api_estoque.entity.Categoria;
import com.davidbrito.api_estoque.exception.RecursoNaoEncontradoException;
import com.davidbrito.api_estoque.exception.RegraNegocioException;
import com.davidbrito.api_estoque.repository.CategoriaRepository;
import com.davidbrito.api_estoque.repository.ProdutoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;
    private final ProdutoRepository produtoRepository;

    public CategoriaService(CategoriaRepository repository, ProdutoRepository produtoRepository){
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    public CategoriaResponseDTO criar(CategoriaRequestDTO dados){
        String nome = dados.nome().trim();
        if (repository.existsByNomeIgnoreCase(nome)){
            throw new RegraNegocioException("Já existe uma categoria com o nome " + nome);
        }

        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        return new CategoriaResponseDTO(repository.save(categoria));
    }

    public List<CategoriaResponseDTO> listar(){
        return repository.findAll(Sort.by("nome"))
                .stream()
                .map(CategoriaResponseDTO::new)
                .toList();
    }

    public CategoriaResponseDTO buscarPorId(Long id){
        return new CategoriaResponseDTO(buscarCategoria(id));
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dados){
        Categoria categoria = buscarCategoria(id);
        String nome = dados.nome().trim();
        if (repository.existsByNomeIgnoreCaseAndIdNot(nome, id)){
            throw new RegraNegocioException("Já existe umna categoria com o nome " + nome);
        }

        categoria.setNome(nome);
        return new CategoriaResponseDTO(repository.save(categoria));
    }

    public void excluir(Long id){
        Categoria categoria = buscarCategoria(id);
        if (produtoRepository.existsByCategoriaId(id)){
            throw new RegraNegocioException("Não é possível excluir uma categoria que possui produtos ");
        }
        repository.delete(categoria);
    }

    private Categoria buscarCategoria(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com id " + id));
    }
}
