package com.unilopers.mercado.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.unilopers.mercado.model.Pedido;
import com.unilopers.mercado.model.ResumoPedido;
import com.unilopers.mercado.model.Item_pedido;
import com.unilopers.mercado.repository.PedidoRepository;
import com.unilopers.mercado.repository.ResumoPedidoRepository;

@RestController
@RequestMapping("/resumo")
public class ResumoPedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ResumoPedidoRepository resumoRepository;

  @PostMapping("/gerar/{idPedido}")
public ResumoPedido gerarResumo(@PathVariable Long idPedido) {

    Pedido pedido = pedidoRepository.findById(idPedido)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

    ResumoPedido resumo = new ResumoPedido();
    resumo.setPedido(pedido);

    int totalItens = pedido.getItens().stream()
            .mapToInt(Item_pedido::getQuantidade)
            .sum();

    resumo.setQuantidadeItens(totalItens);

    BigDecimal totalBruto = BigDecimal.valueOf(pedido.getPrecoTotal());
    resumo.setValorTotalBruto(totalBruto);

    BigDecimal impostos = totalBruto.multiply(BigDecimal.valueOf(0.10));
    resumo.setValorImpostos(impostos);

    resumo.setValorTotalFinal(totalBruto.add(impostos));

    return resumoRepository.save(resumo);
}
   
}
