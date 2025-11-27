package com.unilopers.mercado.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido")  
public class Pedido {

 @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
      @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Item_pedido> itens;


 public Long getIdPedido() {
  return idPedido;
 }

 public void setIdPedido(Long idPedido) {
  this.idPedido = idPedido;
 }

 public double getPrecoTotal() {
  return precoTotal;
 }

 public void setPrecoTotal(double precoTotal) {
  this.precoTotal = precoTotal;
 }

 public String getTipoPagamento() {
  return tipoPagamento;
 }

 public void setTipoPagamento(String tipoPagamento) {
  this.tipoPagamento = tipoPagamento;
 }

 public boolean isPago() {
  return pago;
 }

 public void setPago(boolean pago) {
  this.pago = pago;
 }

 public Cliente getCliente() {
  return cliente;
 }

 public void setCliente(Cliente cliente) {
  this.cliente = cliente;
 }

 public List<Item_pedido> getItens() {
  return itens;
 }

 public void setItens(List<Item_pedido> itens) {
  this.itens = itens;
 }
}


