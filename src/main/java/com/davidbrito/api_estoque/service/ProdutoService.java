package com.davidbrito.api_estoque.service;

import com.davidbrito.api_estoque.dto.ProdutoRequestDTO;
import com.davidbrito.api_estoque.dto.ProdutoResponseDTO;
import com.davidbrito.api_estoque.entity.Produto;
import com.davidbrito.api_estoque.exception.RecursoNaoEncontradoException;
import com.davidbrito.api_estoque.exception.RegraNegocioException;
import com.davidbrito.api_estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
   }

   public ProdutoResponseDTO criar(ProdutoRequestDTO dados){
       Produto produto = new Produto();
       preencherDados(produto, dados);
       return new ProdutoResponseDTO(repository.save(produto));
   }

   public List<ProdutoResponseDTO> listar(){
        return repository.findAll()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();
   }

   public ProdutoResponseDTO buscarPorId(long id){
        return new ProdutoResponseDTO(buscarProduto(id));
   }

   public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dados){
        Produto produto = buscarProduto(id);
        preencherDados(produto, dados);
        return new ProdutoResponseDTO(repository.save(produto));
   }

   public void excluir(Long id){
        Produto produto = buscarProduto(id);
        repository.delete(produto);
   }

   private Produto buscarProduto(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com id " + id ));
   }

   private void preencherDados(Produto produto, ProdutoRequestDTO dados){
        validarPreco(dados);
        produto.setNome(dados.nome());

        produto.setNome(dados.nome());
        produto.setDescricao(dados.descricao());
        produto.setPrecoCusto(dados.precoCusto());
        produto.setPrecoVenda(dados.precoVenda());
        produto.setQuantidadeEstoque(dados.quantidadeEstoque());
   }

   private void validarPreco(ProdutoRequestDTO dados){
        if (dados.precoVenda().compareTo(dados.precoCusto()) < 0){
            throw new RegraNegocioException("O preço de venda não pode ser menor que o preço de custo");
        }
   }
}