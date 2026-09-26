package org.example.explorador_arquivo.classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessamentoDados {
    public static List<List<String>> transformarArquivoEmLista(String caminho) throws FileNotFoundException {
        List<List<String>> linhas = new ArrayList<>();
        String linha;
        try(BufferedReader br = new BufferedReader(new FileReader(caminho))){
            while ((linha = br.readLine()) != null) {
                linhas.add(List.of(linha.split("/")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return linhas;
    }
    public static void transformarListaEmArquivo(String caminho, List<List<String>> linhas) throws FileNotFoundException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(caminho))) {
            int cont;
            for (List<String> linha : linhas) {
                cont = 0;
                for (String i : linha){
                    bufferedWriter.write(i);
                    if ( cont < linha.size()-1){
                        bufferedWriter.write("/");
                        cont++;
                    }

                }
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
