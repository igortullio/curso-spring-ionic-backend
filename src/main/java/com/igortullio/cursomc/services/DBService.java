package com.igortullio.cursomc.services;

import com.igortullio.cursomc.domain.*;
import com.igortullio.cursomc.domain.Enums.EstadoPagamento;
import com.igortullio.cursomc.domain.Enums.TipoCliente;
import com.igortullio.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public void instantiateTestDatabse() throws ParseException {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
        Categoria cat4 = new Categoria(null, "Eletrônicos");
        Categoria cat5 = new Categoria(null, "Jardinagem");
        Categoria cat6 = new Categoria(null, "Decoração");
        Categoria cat7 = new Categoria(null, "Perfumaria");

        Produto pro1 = new Produto(null, "Computador", 2000);
        Produto pro2 = new Produto(null, "Impressora", 800);
        Produto pro3 = new Produto(null, "Mouse", 80);
        Produto pro4 = new Produto(null, "Mesa de Escritório", 300);
        Produto pro5 = new Produto(null, "Toalha", 50);
        Produto pro6 = new Produto(null, "Colcha", 200);
        Produto pro7 = new Produto(null, "TV true color", 1200);
        Produto pro8 = new Produto(null, "Roçadeira", 800);
        Produto pro9 = new Produto(null, "Abajour", 100);
        Produto pro10 = new Produto(null, "Pendente", 180);
        Produto pro11 = new Produto(null, "Shampoo", 90);

        cat1.getProdutos().addAll(Arrays.asList(pro1, pro2, pro3));
        cat2.getProdutos().addAll(Arrays.asList(pro2, pro4));
        cat3.getProdutos().addAll(Arrays.asList(pro5, pro6));
        cat4.getProdutos().addAll(Arrays.asList(pro1, pro2, pro3, pro7));
        cat5.getProdutos().addAll(Arrays.asList(pro8));
        cat6.getProdutos().addAll(Arrays.asList(pro9, pro10));
        cat7.getProdutos().addAll(Arrays.asList(pro11));

        pro1.getCategorias().addAll(Arrays.asList(cat1, cat4));
        pro2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
        pro3.getCategorias().addAll(Arrays.asList(cat1, cat4));
        pro4.getCategorias().addAll(Arrays.asList(cat2));
        pro5.getCategorias().addAll(Arrays.asList(cat3));
        pro6.getCategorias().addAll(Arrays.asList(cat3));
        pro7.getCategorias().addAll(Arrays.asList(cat4));
        pro8.getCategorias().addAll(Arrays.asList(cat5));
        pro9.getCategorias().addAll(Arrays.asList(cat6));
        pro10.getCategorias().addAll(Arrays.asList(cat6));
        pro11.getCategorias().addAll(Arrays.asList(cat7));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(pro1, pro2, pro3, pro4, pro5, pro6, pro7, pro8, pro9, pro10, pro11));

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Maria Silva", "igortullio@hotmail.com", "39378912377", TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1, e2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        ItemPedido ip1 = new ItemPedido(ped1, pro1, 0.00, 1, 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, pro3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, pro2, 100.00, 1, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped1.getItens().addAll(Arrays.asList(ip3));

        pro1.getItens().addAll(Arrays.asList(ip1));
        pro2.getItens().addAll(Arrays.asList(ip3));
        pro3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }

}
