package org.ozis.ui;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

import org.ozis.ui.panel.ProjectsView;
import org.ozis.ui.panel.TopPanel;

public class MainWindow {

    static BorderPane root = new BorderPane(); // основной контейнер
    static StackPane leftPanel = new StackPane(); // Контейнер для реализации отступа левой панели от основной
    static StackPane topPanel = new StackPane(); // Контейнер для реализации отступа верхней панели от основной
    static BorderPane infoPanel = new BorderPane(); // контейнер уже для информации
    
    static BorderPane rightSide = new BorderPane(); // Контейнер для правой части
    
    
    public static void start(Stage stage, String[] projects) {
        // LeftSide
        // Левая панель с обёрткой для отступов от краёв
        leftPanel.setPadding(new Insets(10));
        leftPanel.getChildren().add(ProjectsView.getLeftPanel(projects));
        
        // RightSide
        // Верхняя панель с обёрткой для отступов от краёв
        topPanel.setPadding(new Insets(10));
        topPanel.getChildren().add(TopPanel.get_btns());

        // контейнер правой части
        rightSide.setTop(topPanel);
        rightSide.setCenter(infoPanel);
        
        // Основной контейнер
        root.setLeft(leftPanel);
        root.setCenter(rightSide);
        root.setStyle("-fx-background-color: #cfcfcf;");




        
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Project Manager");
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }
}
