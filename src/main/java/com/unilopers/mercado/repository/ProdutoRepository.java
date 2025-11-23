package com.unilopers.mercado.repository;

import com.unilopers.mercado.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto>findByNomeProduto(String nomeProduto);
}
