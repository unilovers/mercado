package com.unilopers.mercado.controller;

import com.unilopers.mercado.model.Produto;
import com.unilopers.mercado.repository.CategoriaRepository;
import com.unilopers.mercado.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;


    @GetMapping
    public List<Produto> list() {
        return produtoRepository.findAll();
    }


    @GetMapping("/buscaPeloId/{id}")
    public ResponseEntity<Produto> buscaPeloId(@PathVariable Long id) {
        Optional<Produto> usuario = produtoRepository.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/criar")
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        // Check unique nome
        if (produto.getNomeProduto() != null) {
            Optional<Produto> existing = produtoRepository.findByNomeProduto(produto.getNomeProduto());
            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        Produto saved = produtoRepository.save(produto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getIdProduto())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtual) {
        Optional<Produto> opt = produtoRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Produto produto = opt.get();

        if (produtoAtual.getNomeProduto() != null && !produtoAtual.getNomeProduto().equalsIgnoreCase(produto.getNomeProduto())) {
            Optional<Produto> byNome = produtoRepository.findByNomeProduto(produtoAtual.getNomeProduto());
            if (byNome.isPresent() && !byNome.get().getIdProduto().equals(id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            produto.setNomeProduto(produtoAtual.getNomeProduto());
        }

        if (produtoAtual.getPrecoProduto() != 0.00) {
            produto.setPrecoProduto(produtoAtual.getPrecoProduto());
        }
        if (produtoAtual.getQuantidadeEstoque() >= 0) {
            produto.setQuantidadeEstoque(produtoAtual.getQuantidadeEstoque());
        }

        if (produtoAtual.getValidade() != null) {
            LocalDate hoje = LocalDate.now();

            if (produtoAtual.getValidade().isBefore(hoje)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            produto.setValidade(produtoAtual.getValidade());
        }


        Produto saved = produtoRepository.save(produto);
        return ResponseEntity.ok(saved);
    }


    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        Optional<Produto> opt = produtoRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
