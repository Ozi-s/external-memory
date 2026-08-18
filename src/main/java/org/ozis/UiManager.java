package org.ozis;

import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UiManager {

    BorderPane root = new BorderPane();
    VBox LeftPanel = new VBox(10);
    
    VBox projects = new VBox(10);
    
    StackPane projectsWrapper = new StackPane(LeftPanel);
    Label projectsTitle = new Label("Проекты:");
    
    public void start(Stage stage) {
        // левая панель 
        LeftPanel.setPadding(new Insets(15));
        LeftPanel.setMinWidth(220);
        LeftPanel.setStyle("""
        -fx-background-color: #141414;
        -fx-background-radius: 10;
        """);

        // тайтл левой панели
        projectsTitle.setStyle("""
        -fx-text-fill: #cfcfcf;
        -fx-font-size: 22px;
        -fx-font-weight: bold;
        """);
        LeftPanel.getChildren().add(projectsTitle);
        
        // Обёртка для отступов от краёв BorderPane
        projectsWrapper.setPadding(new Insets(10));

        // Основной контейнер
        root.setLeft(projectsWrapper);
        root.setStyle("""
        -fx-background-color: #cfcfcf;
        """);

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Project Manager");
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }
}
