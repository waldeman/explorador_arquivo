package org.example.explorador_arquivo.classes;

public class Arquivo extends Item {
    private String tipo;

    public Arquivo (String nome, String tipo) {
        super(nome);
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
