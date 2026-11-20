package com.davidbrito.api_estoque.service;

import com.davidbrito.api_estoque.dto.ItemVendaRequestDTO;
import com.davidbrito.api_estoque.dto.VendaRequestDTO;
import com.davidbrito.api_estoque.dto.VendaResponseDTO;
import com.davidbrito.api_estoque.entity.ItemVenda;
import com.davidbrito.api_estoque.entity.Produto;
import com.davidbrito.api_estoque.entity.Venda;
import com.davidbrito.api_estoque.exception.RecursoNaoEncontradoException;
import com.davidbrito.api_estoque.exception.RegraNegocioException;
import com.davidbrito.api_estoque.repository.ProdutoRepository;
import com.davidbrito.api_estoque.repository.VendaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository){
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public VendaResponseDTO registrar(VendaRequestDTO dados){
        Venda venda = new Venda();
        venda.setDataHora(LocalDateTime.now());

        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal lucroTotal = BigDecimal.ZERO;

        for (ItemVendaRequestDTO itemDados : dados.itens()){
            Produto produto = produtoRepository.findById(itemDados.produtoId())
                    .orElseThrow(()-> new RecursoNaoEncontradoException(
                            "Produto não encontrado com id " + itemDados.produtoId()
                    ));

            int quantidade = itemDados.quantidade();
            if (produto.getQuantidadeEstoque() < quantidade) {
                throw new RegraNegocioException(
                        "Estoque insulficiente para o produto " + produto.getNome()
                                + ". Disponível: " + produto.getQuantidadeEstoque()
                                + ", solicitado: " + quantidade);
            }

            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);

            BigDecimal qtd = BigDecimal.valueOf(quantidade);
            BigDecimal subTotal = produto.getPrecoVenda().multiply(qtd);
            BigDecimal lucroItem = produto.getPrecoVenda()
                    .subtract(produto.getPrecoCusto())
                    .multiply(qtd);

            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(produto.getPrecoVenda());
            item.setCustoUnitario(produto.getPrecoCusto());
            item.setSubTotal(subTotal);
            venda.adicionarItem(item);

            valorTotal = valorTotal.add(subTotal);
            lucroTotal = lucroTotal.add(lucroItem);
            }

        venda.setValorTotal(valorTotal);
        venda.setLucroTotal(lucroTotal);

        return new VendaResponseDTO(vendaRepository.save(venda));
        }

    @Transactional(readOnly = true)
    public List<VendaResponseDTO> listar()  {
            return vendaRepository.findAll()
                    .stream()
                    .map(VendaResponseDTO::new)
                    .toList();
        }

    @Transactional(readOnly = true)
    public VendaResponseDTO buscarPorId(Long id){
            Venda venda = vendaRepository.findById(id)
                    .orElseThrow(()-> new RecursoNaoEncontradoException("Venda não encontrada com id " + id));
            return new VendaResponseDTO(venda);
    }
}

