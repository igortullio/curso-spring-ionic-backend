package com.igortullio.cursomc;

import com.igortullio.cursomc.domain.Categoria;
import com.igortullio.cursomc.domain.Produto;
import com.igortullio.cursomc.repositories.CategoriaRepository;
import com.igortullio.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        Produto pro1 = new Produto(null, "Computador", 2000);
        Produto pro2 = new Produto(null, "Impressora", 800);
        Produto pro3 = new Produto(null, "Mouse", 80);

        cat1.getProdutos().addAll(Arrays.asList(pro1, pro2, pro3));
        cat2.getProdutos().addAll(Arrays.asList(pro2));

        pro1.getCategorias().addAll(Arrays.asList(cat1));
        pro2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        pro3.getCategorias().addAll(Arrays.asList(cat1));


        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(pro1, pro2, pro3));

    }
}
