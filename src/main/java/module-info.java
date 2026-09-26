module org.example.explorador_arquivo {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.explorador_arquivo to javafx.fxml;
    exports org.example.explorador_arquivo;
}