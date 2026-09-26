package org.example.explorador_arquivo.classes;

import java.util.ArrayList;
import java.util.List;

public class Diretorio extends Item{
    private List<Item> itens;

    public Diretorio (String nome, List<Item> itens) {
        super(nome);
        this.itens = new ArrayList<>(itens);
    }

    public List<Item> getItens() {
        return this.itens;
    }
    public void setItens(List<Item> itens) {
        if (itens == null) {
            this.itens = new ArrayList<>();
        } else {
            this.itens = new ArrayList<>(itens);
        }
    }
}
