package org.example.explorador_arquivo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.explorador_arquivo.classes.Arquivo;
import org.example.explorador_arquivo.classes.Diretorio;
import org.example.explorador_arquivo.classes.Item;
import org.example.explorador_arquivo.classes.ProcessamentoDados;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HelloController {
    private Diretorio c;
    private List<Item> pastas;
    private Diretorio diretorioSelecionado;
    private Item itemSelecionado;
    @FXML
    private VBox vboxLabels;
    @FXML
    private VBox vboxLabels2;
    @FXML
    private Label labelCaminho;

    public HelloController() throws FileNotFoundException {
        this.pastas = new ArrayList<>();
        iniciarObjetosItens();
        this.diretorioSelecionado = this.c;
        this.itemSelecionado = this.c;
    }

    @FXML
    public void initialize() {
        iniciarTextoLabels();
        atualizarCaminho();

    }

    @FXML
    public void novoDiretorio(ActionEvent actionEvent) throws FileNotFoundException {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Novo Diretório");
        dialogo.setHeaderText("Digite o nome do Diretório:");
        Optional<String> resultado = dialogo.showAndWait();
        if (!resultado.isEmpty()) {
            this.diretorioSelecionado.getItens().add(new Diretorio(resultado.get(), this.diretorioSelecionado));
        }
        if (this.diretorioSelecionado.equals(this.c)) {
            iniciarTextoLabels();
            salvarArvore();
            return;
        }
        iniciarTextoLabels2(this.diretorioSelecionado);
        salvarArvore();
    }

    @FXML
    public void novoArquivo(ActionEvent actionEvent) throws FileNotFoundException {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Novo Arquivo");
        dialogo.setHeaderText("Digite o nome do Arquivo:");
        Optional<String> resultado = dialogo.showAndWait();
        if (!resultado.isEmpty()) {
            String nomeArquivo = resultado.get();
            String[] lista = nomeArquivo.split("\\.");
            this.diretorioSelecionado.getItens().add(new Arquivo(lista[0], lista[1], this.diretorioSelecionado));
        }
        if (this.diretorioSelecionado.equals(this.c)) {
            iniciarTextoLabels();
            salvarArvore();
            return;
        }
        iniciarTextoLabels2(this.diretorioSelecionado);
        salvarArvore();
    }

    @FXML
    public void renomear(ActionEvent actionEvent) throws FileNotFoundException {
        Diretorio pai = this.itemSelecionado.getDiretorioPai();
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Novo Nome");
        dialogo.setHeaderText("Digite o Novo Nome:");
        Optional<String> resultado = dialogo.showAndWait();
        if (pai != null && !resultado.isEmpty()) {
            for (Item item : pai.getItens()) {
                if (item.equals(itemSelecionado)) {
                    if (item instanceof Diretorio) {
                        item.setNome(resultado.get());
                    } else if (item instanceof Arquivo) {
                        String[] lista = resultado.get().split("\\.");
                        item.setNome(lista[0]);
                        ((Arquivo) item).setTipo(lista[1]);
                    }
                }
            }
            if (this.diretorioSelecionado.getDiretorioPai().equals(this.c)) {
                iniciarTextoLabels();
                salvarArvore();
                return;
            }
            iniciarTextoLabels2(this.diretorioSelecionado);
            salvarArvore();
            atualizarCaminho();
        }

    }

    @FXML
    public void excluir(ActionEvent actionEvent) throws FileNotFoundException {
        Diretorio pai = this.itemSelecionado.getDiretorioPai();
        if (pai != null) {
            pai.getItens().remove(this.itemSelecionado);
            this.diretorioSelecionado = pai;
            this.itemSelecionado = pai;
            if (this.diretorioSelecionado.equals(this.c)) {
                iniciarTextoLabels();
                salvarArvore();

                return;
            }
            iniciarTextoLabels2(this.diretorioSelecionado);
            salvarArvore();
            atualizarCaminho();
        }

    }

    @FXML
    public void voltar() {
        if (this.diretorioSelecionado.equals(this.c)) {
            return;
        }
        this.diretorioSelecionado = this.diretorioSelecionado.getDiretorioPai();
        this.itemSelecionado = this.diretorioSelecionado;
        if (this.diretorioSelecionado.equals(this.c)) {
            iniciarTextoLabels();
            atualizarCaminho();
            iniciarTextoLabels2(this.diretorioSelecionado);
            return;
        }
        iniciarTextoLabels2(this.diretorioSelecionado);
        atualizarCaminho();
    }

    public HBox criarItemVisual(Item item) {
        HBox hBox = new HBox();
        hBox.getStyleClass().add("h-box");
        hBox.setSpacing(10);
        Label label = null;
        Image image;
        ImageView imageView = null;

        if (item instanceof Diretorio) {
            image = new Image(getClass().getResourceAsStream("/org/example/explorador_arquivo/imagens/foto_pasta.png"));
            imageView = new ImageView(image);
            label = new Label(item.getNome());
        } else if (item instanceof Arquivo) {
            if (((Arquivo) item).getTipo().equals("pdf")) {
                image = new Image(getClass().getResourceAsStream("/org/example/explorador_arquivo/imagens/icone_pdf.png"));
            } else if (((Arquivo) item).getTipo().equals("py")) {
                image = new Image(getClass().getResourceAsStream("/org/example/explorador_arquivo/imagens/icone_python.png"));
            } else if (((Arquivo) item).getTipo().equals("java")) {
                image = new Image(getClass().getResourceAsStream("/org/example/explorador_arquivo/imagens/icone_java.png"));
            } else if (((Arquivo) item).getTipo().equals("doc")) {
                image = new Image(getClass().getResourceAsStream("/org/example/explorador_arquivo/imagens/icone_doc.png"));
            } else {
                image = new Image(getClass().getResourceAsStream("/org/example/explorador_arquivo/imagens/ícone_texto.png"));
            }
            imageView = new ImageView(image);
            label = new Label(item.getNome() + "." + ((Arquivo) item).getTipo());
        }
        imageView.setFitWidth(20);
        imageView.setPreserveRatio(true);
        hBox.getChildren().add(imageView);
        hBox.getChildren().add(label);
        return hBox;
    }

    public void atualizarCaminho() {
        List<String> caminho = new ArrayList<>();
        Item itemAtual = this.itemSelecionado;
        if (itemAtual instanceof Diretorio) {
            caminho.add(itemAtual.getNome());
        } else if (itemAtual instanceof Arquivo) {
            caminho.add(itemAtual.getNome() + "." + ((Arquivo) itemAtual).getTipo());
        }

        while (itemAtual.getDiretorioPai() != null) {
            itemAtual = itemAtual.getDiretorioPai();
            caminho.add(itemAtual.getNome());
        }
        String texto = "";
        for (int i = caminho.size() - 1; i >= 0; i--) {
            texto += caminho.get(i);
            if (i != 0) {
                texto += " > ";
            }

        }
        this.labelCaminho.setText(texto);

    }

    public void salvarArvore() throws FileNotFoundException {
        List<String> caminhoAtual = new ArrayList<>();
        caminhoAtual.add("C:");
        List<List<String>> caminhos = new ArrayList<>();
        List<List<String>> caminhoFinal = caminhoRecursivo(caminhos, this.c.getItens(), caminhoAtual);
        ProcessamentoDados.transformarListaEmArquivo(caminhoFinal, "dados/caminho.txt");

    }

    public void iniciarTextoLabels() {
        vboxLabels.getChildren().clear();
        for (Item item : c.getItens()) {
            HBox hBox = criarItemVisual(item);
            configurarCliqueHBox(hBox, item);
            vboxLabels.getChildren().add(hBox);
        }
        iniciarTextoLabels2(this.diretorioSelecionado);
    }

    public void iniciarTextoLabels2(Diretorio diretorio) {
        vboxLabels2.getChildren().clear();
        if (this.itemSelecionado.equals(this.c)) {
            Label label = new Label();
            label.setText("Nenhum arquivo ou pasta selecionado.");
            vboxLabels2.getChildren().add(label);
            return;
        }
        for (Item item : diretorio.getItens()) {
            HBox hBox = criarItemVisual(item);
            configurarCliqueHBox(hBox, item);
            vboxLabels2.getChildren().add(hBox);
        }
    }

    public void configurarCliqueHBox(HBox hBox, Item item) {
        hBox.setOnMouseClicked(event -> {
            if (item instanceof Diretorio) {
                this.diretorioSelecionado = (Diretorio) item;
                this.itemSelecionado = item;
                iniciarTextoLabels2((Diretorio) item);

            } else if (item instanceof Arquivo) {
                this.itemSelecionado = item;
            }
            atualizarCaminho();

        });
    }

    public void iniciarObjetosItens() throws FileNotFoundException {
        this.c = new Diretorio("C:");

        for (List<String> caminho : ProcessamentoDados.transformarArquivoEmLista("dados/caminho.txt")) {

            Diretorio atual = c;

            for (int i = 1; i < caminho.size(); i++) {

                String nome = caminho.get(i);
                Item itemExistente = null;

                for (Item item : atual.getItens()) {
                    if (item.getNome().equals(nome)) {
                        itemExistente = item;
                        break;
                    }
                }

                if (itemExistente != null) {
                    if (itemExistente instanceof Diretorio) {
                        atual = (Diretorio) itemExistente;
                    }
                    continue;
                }

                if (i == caminho.size() - 1 && nome.contains(".")) {
                    String[] partes = nome.split("\\.");
                    atual.getItens().add(new Arquivo(partes[0], partes[1], atual));
                } else {
                    Diretorio novoDiretorio = new Diretorio(nome, atual);
                    atual.getItens().add(novoDiretorio);
                    atual = novoDiretorio;
                }
            }
        }
    }

    public static List<List<String>> caminhoRecursivo(List<List<String>> caminhos, List<Item> itens, List<String> caminhoAtual) {
        if (itens.isEmpty()) {
            return caminhos;
        }
        if (itens.get(0) instanceof Diretorio) {
            caminhoAtual.add(itens.get(0).getNome());
            caminhos.add(new ArrayList<>(caminhoAtual));
            Diretorio diretorio = (Diretorio) itens.get(0);
            caminhoRecursivo(caminhos, diretorio.getItens(), caminhoAtual);
            caminhoAtual.remove(caminhoAtual.size() - 1);
            caminhoRecursivo(caminhos, itens.subList(1, itens.size()), caminhoAtual);
        }
        if (itens.get(0) instanceof Arquivo) {
            caminhoAtual.add(itens.get(0).getNome() + "." + ((Arquivo) itens.get(0)).getTipo());
            caminhos.add(new ArrayList<>(caminhoAtual));
            caminhoAtual.remove(caminhoAtual.size() - 1);
            caminhoRecursivo(caminhos, itens.subList(1, itens.size()), caminhoAtual);
        }

        return caminhos;
    }


}
