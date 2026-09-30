package org.example.explorador_arquivo.classes;

public class Item {
    private String nome;
    private Diretorio diretorioPai;

    public Item(String nome, Diretorio diretorioPai) {
        this.nome = nome;
        this.diretorioPai = diretorioPai;
    }

    public Diretorio getDiretorioPai() {
        return diretorioPai;
    }

    public void setDiretorioPai(Diretorio diretorioPai) {
        this.diretorioPai = diretorioPai;
    }

    public Item (String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
