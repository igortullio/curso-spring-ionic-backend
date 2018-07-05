package com.igortullio.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@EqualsAndHashCode
@Setter
@Getter
@Entity
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @EqualsAndHashCode.Exclude
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instante;

    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "endereco_de_entrega_id")
    private Endereco enderecoDeEntrega;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido() {
    }

    public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
        super();
        this.id = id;
        this.instante = instante;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public double getValorTotal(){
        double soma = 0;
        for (ItemPedido itemPedido : itens){
            soma += itemPedido.getSubTotal();
        }
        return soma;
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        final StringBuilder sb = new StringBuilder();
        sb.append("Pedido número: ").append(getId());
        sb.append(", Instante: ").append(sdf.format(getInstante()));
        sb.append(", Cliente: ").append(getCliente().getNome());
        sb.append(", Situação do pagamento: ").append(getPagamento().getEstadoPagamento().getDescricao());
        sb.append("\nDetalhes: \n");
        getItens().forEach(itemPedido -> sb.append(itemPedido.toString()));
        sb.append("Valor total: ").append(nf.format(getValorTotal()));
        return sb.toString();
    }
}
