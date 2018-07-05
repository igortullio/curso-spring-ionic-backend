package com.igortullio.cursomc.domain.Enums;

import lombok.Getter;

@Getter
public enum Perfil {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private int cod;
    private String descricao;

    Perfil(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static Perfil toEnum(Integer cod){
        if (cod == null){
            return null;
        }
        for (Perfil estadoPagamento : Perfil.values()){
            if (cod.equals(estadoPagamento.getCod())){
                return estadoPagamento;
            }
        }
        throw new IllegalArgumentException("Id inválido: " +cod);
    }
}
