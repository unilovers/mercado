package com.unilopers.mercado.service;
import com.unilopers.mercado.model.Pedido;
import com.unilopers.mercado.model.ResumoPedido;
import com.unilopers.mercado.repository.PedidoRepository;
import com.unilopers.mercado.repository.ResumoPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class ResumoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ResumoPedidoRepository resumoPedidoRepository;


    public ResumoPedido gerarResumo(Long idPedido) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));


        ResumoPedido resumo = new ResumoPedido();
        resumo.setPedido(pedido);

        BigDecimal totalBruto = BigDecimal.valueOf(pedido.getPrecoTotal());
        resumo.setValorTotalBruto(totalBruto);

        BigDecimal impostos = totalBruto.multiply(BigDecimal.valueOf(0.10));
        resumo.setValorImpostos(impostos);

        resumo.setValorTotalFinal(totalBruto.add(impostos));

        return resumoPedidoRepository.save(resumo);
    }
}
