package org.ozis.ui;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

public class MainWindow {

    static BorderPane root = new BorderPane(); // основной контейнер
    static StackPane projectsWrapper = new StackPane(); //(LeftPanel) // Контейнер для реализации отступа левой панели от основной
    
    public static void start(Stage stage, String[] projects) {
        BorderPane LeftPanel = ProjectsView.start(projects);
        // Обёртка для отступов от краёв BorderPane
        projectsWrapper.setPadding(new Insets(10));
        projectsWrapper.getChildren().add(LeftPanel);
        // Основной контейнер
        root.setLeft(projectsWrapper);
        root.setStyle("-fx-background-color: #cfcfcf;");

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Project Manager");
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }
}
