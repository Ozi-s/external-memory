package org.ozis.ui.panel;

import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.geometry.Insets;

import org.ozis.dao.ProjectDao;
import org.ozis.entity.Project;
import org.ozis.ui.ActionManager;

public class LeftPanel {
    private final ProjectDao projectDao = new ProjectDao(Project.class);
    private final ActionManager actionManager;

    private final BorderPane root = new BorderPane();
    private final VBox projectList = new VBox(10);
    private final Label projectLabel = new Label("Проекты:");
    private final Button projectAddButton = new Button("+");

    // получаем UiManager
    public LeftPanel(ActionManager actionManager) {
        this.actionManager = actionManager;
        setup();
        refreshProjects();
    }
    
    public BorderPane getRoot() { return root; }

    private void setup() {
        root.getStyleClass().add("panel");
        root.setMinWidth(220);

        projectLabel.getStyleClass().add("panel-title");

        projectList.setMinWidth(200);

        projectAddButton.getStyleClass().addAll("btn", "btn-add");
        projectAddButton.setOnAction(e -> onAddProject());

        root.setTop(projectLabel);
        root.setCenter(projectList);
        root.setBottom(projectAddButton);

        BorderPane.setMargin(projectLabel, new Insets(0, 0, 10, 0));
    }

    // обновить список проектов
    private void refreshProjects() {
        System.out.println("refreshProjects вызван");
        
        projectList.getChildren().clear();
        projectDao.findAll().forEach(p -> showProject(p.getName()));
    }

    // добавить проект в поле проектов
    private void showProject(String name) {
        Button btn = new Button(name);
        btn.getStyleClass().addAll("btn", "project-btn");
        btn.setOnAction(e -> {
            System.out.println("Открываю проект: " + name);
            actionManager.openProject(name);
        });
        projectList.getChildren().add(btn);
    }

    private void onAddProject() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Добавить новый проект");
        dialog.setContentText("Введите название проекта:");

        dialog.showAndWait().ifPresent(name -> {
            if (name.isBlank()) return;
            projectDao.save(new Project("here", name));
            refreshProjects();
        });
    }
    
}
