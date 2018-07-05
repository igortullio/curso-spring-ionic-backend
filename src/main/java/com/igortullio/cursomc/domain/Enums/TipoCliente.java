package com.igortullio.cursomc.domain.Enums;

import lombok.Getter;

@Getter
public enum TipoCliente {

    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private int cod;
    private String descricao;

    private TipoCliente(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static TipoCliente toEnum(Integer cod){
        if (cod == null){
            return null;
        }
        for (TipoCliente tipoCliente : TipoCliente.values()){
            if (cod.equals(tipoCliente.getCod())){
                return tipoCliente;
            }
        }

        throw new IllegalArgumentException("Id inválido: " +cod);
    }
}
