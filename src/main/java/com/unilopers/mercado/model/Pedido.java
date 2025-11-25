package com.unilopers.mercado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido")  
public class Pedido {

 @Id
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "preco_total", nullable = false)
    private double precoTotal;

    @Column(name = "tipo_pagamento", nullable = false, length = 50)
    private String tipoPagamento;

    @Column(nullable = false)
    private boolean pago;

    @ManyToOne
    @JoinColumn(name = "fk_cliente", nullable = false)
    private Cliente cliente;

}


