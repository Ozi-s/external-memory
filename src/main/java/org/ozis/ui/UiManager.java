package org.ozis.ui;

import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

import org.ozis.UiData;

public class UiManager {

    BorderPane root = new BorderPane(); // основной контейнер
    StackPane projectsWrapper = new StackPane(); //(LeftPanel) // Контейнер для реализации отступа левой панели от основнойw
    
    public void start(UiData uidata) {
        BorderPane LeftPanel = ProjectsView.start(uidata.getProjects());
        // Обёртка для отступов от краёв BorderPane
        projectsWrapper.setPadding(new Insets(10));
        projectsWrapper.getChildren().add(LeftPanel);
        // Основной контейнер
        root.setLeft(projectsWrapper);
        root.setStyle("-fx-background-color: #cfcfcf;");

        Scene scene = new Scene(root, 1000, 700);
        uidata.getStage().setTitle("Project Manager");
        uidata.getStage().setMinWidth(700);
        uidata.getStage().setMinHeight(500);
        uidata.getStage().setScene(scene);
        uidata.getStage().show();
    }
}
