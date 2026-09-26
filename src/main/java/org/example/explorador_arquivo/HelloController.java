package org.example.explorador_arquivo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.explorador_arquivo.classes.ProcessamentoDados;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label[] labels;
    static {
        String caminho = "dados/caminho.txt";
        List<List<String>> lista = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            lista.add(new ArrayList<>());
        }
        lista.get(0).add("c/area_de_trabalho");
        lista.get(1).add("c/dowloads");
        lista.get(2).add("c/documentos");
        lista.get(3).add("c/imagens");

        try {
            ProcessamentoDados.transformarListaEmArquivo(caminho, lista );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
