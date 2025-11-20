package com.unilopers.mercado.repository;

import com.unilopers.mercado.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
