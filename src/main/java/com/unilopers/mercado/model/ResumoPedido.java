package com.unilopers.mercado.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "resumo_pedido")
public class ResumoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resumo")
    private Long idResumo;

    @OneToOne
    @JoinColumn(name = "fk_pedido", nullable = false, unique = true)
    private Pedido pedido;

    @Column(name = "quantidade_itens", nullable = false)
    private int quantidadeItens;

    @Column(name = "valor_total_bruto", nullable = false)
    private BigDecimal valorTotalBruto;

    @Column(name = "valor_impostos", nullable = false)
    private BigDecimal valorImpostos;

    @Column(name = "valor_total_final", nullable = false)
    private BigDecimal valorTotalFinal;
}
