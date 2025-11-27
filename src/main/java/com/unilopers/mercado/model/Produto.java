package com.unilopers.mercado.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    @Column(name = "nome_produto", nullable = false)
    private String nomeProduto;

    @Column(name = "preco_produto", nullable = false)
    private double precoProduto;

    @Column(name = "quantidade_estoque", nullable = false)
    private int quantidadeEstoque;

    @Column(name = "validade", nullable = false)
    private LocalDate validade;

    @Column(name = "fk_categoria", nullable = false)
    private Long idCategoria;




}
