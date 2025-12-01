package com.unilopers.mercado.controller;

import com.unilopers.mercado.model.Categoria;
import com.unilopers.mercado.model.Produto;
import com.unilopers.mercado.repository.CategoriaRepository;
import com.unilopers.mercado.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;


    @GetMapping
    public List<Produto> list() {
        return produtoRepository.findAll();
    }


    @GetMapping("/buscaPeloId/{id}")
    public ResponseEntity<?> buscaPeloId(@PathVariable Long id) throws Exception{
        try {
            Optional<Produto> produto = produtoRepository.findById(id);
            if (produto.isEmpty()) {
                throw new Exception("Não existe um produto com esse ID.");
            }
            return ResponseEntity.status(201).body(produto);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Ocorreu um erro: " + e.getMessage());
        }
    }


    @PostMapping("/criar")
    public ResponseEntity<?> criarProduto(@RequestBody Produto produto) throws Exception {
        try {
            if (produto.getNomeProduto() != null) {
                Optional<Produto> existing = produtoRepository.findByNomeProduto(produto.getNomeProduto());
                if (existing.isPresent()) {
                    throw new Exception("Já existe um produto com esse nome.");
                }
            }

            if (produto.getPrecoProduto() <= 0.00){
                throw new Exception("Preço do produto inválido(Não pode ser menor ou igual a 0.");
            }

            LocalDate hoje = LocalDate.now();
            if (produto.getValidade().isBefore(hoje)){
                System.out.println(hoje);
                throw new Exception("Data de válidade inválida(A data de válidade deve ser maior que a data atual).");
            }

            if (produto.getQuantidadeEstoque() <= 0){
                throw new Exception("A quantidade em estoque do produto não pode ser negativa.");
            }

            Optional<Categoria> idCategoria = categoriaRepository.findById(produto.getCategoria().getId());
            if (idCategoria.isEmpty()){
                throw new Exception("Categoria selecionada não existe.");
            }

            Produto saved = produtoRepository.save(produto);
            return ResponseEntity.status(201).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Ocorreu um erro:\n" + e.getMessage());
        }
    }


    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarProduto(@RequestBody Produto produtoAtual) throws Exception {
        try {
            if (produtoAtual.getIdProduto() == null) {
                throw new Exception("O id do produto é obrigatório para atualizar.");
            }

            if (!produtoRepository.existsById(produtoAtual.getIdProduto())) {
                throw new Exception("Usuário com id " + produtoAtual.getIdProduto() + " não encontrado.");
            }

            Optional<Produto> opt = produtoRepository.findById(produtoAtual.getIdProduto());
            if (opt.isEmpty()) {
                throw new Exception("Produto com ID informado não encontrado.");
            }
            Produto produto = opt.get();

            if (produtoAtual.getNomeProduto() != null && !produtoAtual.getNomeProduto().equalsIgnoreCase(produto.getNomeProduto())) {
                Optional<Produto> findbyNome = produtoRepository.findByNomeProduto(produtoAtual.getNomeProduto());
                if (findbyNome.isPresent() && !findbyNome.get().getIdProduto().equals(produtoAtual.getIdProduto())) {
                    throw new Exception("Já existe um produto com esse nome.");
                }
                produto.setNomeProduto(produtoAtual.getNomeProduto());
            }else {
                throw new Exception("O nome do produto não pode ser nulo.");
            }

            if (produtoAtual.getPrecoProduto() > 0.00) {
                produto.setPrecoProduto(produtoAtual.getPrecoProduto());
            }else{
                throw new Exception("O preço do produto deve ser maior que 0.");
            }
            if (produtoAtual.getQuantidadeEstoque() >= 0) {
                produto.setQuantidadeEstoque(produtoAtual.getQuantidadeEstoque());
            }else{
                throw new Exception("A quantidade em estoque do produto deve ser maior ou igual a 0");
            }

            if (produtoAtual.getValidade() != null) {
                LocalDate hoje = LocalDate.now();

                if (produtoAtual.getValidade().isBefore(hoje)) {
                    throw new Exception("A data de validade deve ser maior que a data atual.");
                }
                produto.setValidade(produtoAtual.getValidade());
            }

            if (produtoAtual.getCategoria() == null || produtoAtual.getCategoria().getId() == null) {
                throw new Exception("A categoria do produto é obrigatória.");
            }
            Optional<Categoria> idCategoria = categoriaRepository.findById(produtoAtual.getCategoria().getId());
            if (idCategoria.isEmpty()){
                throw new Exception("A categoria selecionada não existe.");
            } else {
                produto.setCategoria(idCategoria.get());
            }

            Produto saved = produtoRepository.save(produto);
            return ResponseEntity.ok(saved);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Ocorreu um erro:\n" + e.getMessage());
        }
    }


    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable Long id) throws Exception {
        try {
            Optional<Produto> opt = produtoRepository.findById(id);
            if (opt.isEmpty()) {
                throw new Exception("Produto com ID informado não encontrado.");
            }
            produtoRepository.deleteById(id);
            return ResponseEntity.status(200).body("Produto deletado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(400).body("Ocorreu um erro:\n" + e.getMessage());
        }
    }
}
