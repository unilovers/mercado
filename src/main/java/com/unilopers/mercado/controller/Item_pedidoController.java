package com.unilopers.mercado.controller;

import com.unilopers.mercado.model.Item_pedido;
import com.unilopers.mercado.repository.Item_pedidoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-pedido")
public class Item_pedidoController {

    private final Item_pedidoRepository repo;

    public Item_pedidoController(Item_pedidoRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Item_pedido create(@RequestBody Item_pedido item) {
        return repo.save(item);
    }

    @GetMapping
    public List<Item_pedido> listAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Item_pedido findById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Item_pedido update(@PathVariable Long id, @RequestBody Item_pedido dados) {
        return repo.findById(id).map(item -> {
            item.setQuantidade(dados.getQuantidade());
            item.setPrecoUnitario(dados.getPrecoUnitario());
            item.setPedido(dados.getPedido());
            item.setProduto(dados.getProduto());
            return repo.save(item);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}