package org.example.explorador_arquivo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Image image;
    @Override
    public void start(Stage stage) throws IOException {
        image = new Image(getClass().getResourceAsStream("/org/example/explorador_arquivo/imagens/foto_pasta.png"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1000, 700);
        stage.setTitle("Explorador de Arquivo");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }
}
