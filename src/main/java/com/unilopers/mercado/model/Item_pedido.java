package com.unilopers.mercado.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "item_pedido")
public class Item_pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "fk_produto", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private int quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private double precoUnitario;

}