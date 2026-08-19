package org.ozis;

import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UiManager {

    BorderPane root = new BorderPane(); // основной контейнер
    BorderPane LeftPanel = new BorderPane(); // левая панель
    
    StackPane projectsWrapper = new StackPane(LeftPanel); // Контейнер для реализации отступа левой панели от основной
    Label projectsTitle = new Label("Проекты:"); // Тайтл сверху
    VBox projects = new VBox(10); // Лист проектов
    Button projectAddBtn = new Button("+");
    
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
        projects.setMinWidth(200);
        // projects.
        projects.setStyle("""
        -fx-background-color: #b6b6b6;
        """);

        LeftPanel.setPadding(new Insets(10));
        LeftPanel.setTop(projectsTitle);
        LeftPanel.setCenter(projects);
        LeftPanel.setBottom(projectAddBtn);
        
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
