package org.ozis.ui.panel;

import java.util.List;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.geometry.Insets;
import java.util.Optional;

import org.ozis.dao.ProjectDao;
import org.ozis.entity.Project;

public class LeftPanel {
    private final ProjectDao projectDao = new ProjectDao(Project.class);

    private final BorderPane root = new BorderPane();
    private final VBox projectList = new VBox(10);
    private final Label projectLabel = new Label("Проекты:");
    private final Button projectButton = new Button("+");

    // получаем UiManager
    public LeftPanel() {
        refreshProjects();
        setup();
    
        projectButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Добавить новый проект");
            dialog.setContentText("Введите название проекта:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String projectName= result.get();
                Project newProject=new Project("here",projectName);
                projectDao.save(newProject);
                refreshProjects();
            }
        });

        root.setTop(projectLabel);
        root.setCenter(projectList);
        root.setBottom(projectButton);
    }

    public BorderPane getRoot() {
        return root;
    }

    // обновить список проектов
    private void refreshProjects() {
        System.out.println("refreshProjects вызван");
        projectList.getChildren().clear();
        List<Project> allProjects = projectDao.findAll();
        System.out.println("Найдено проектов: " + allProjects.size());
        for (Project p : allProjects) {
            System.out.println("Добавляем проект: " + p.getName());
            show_new_project(p.getName());
        }
    }

    // добавить проект в поле проектов
    private void show_new_project(String project_name) {
        Button projectBtn = new Button(project_name);
        projectBtn.setMaxWidth(Double.MAX_VALUE);
        projectBtn.setStyle("""
            -fx-background-color: #7f49b4;
            -fx-text-fill: #cfcfcf;
            -fx-font-size: 20px;
        """);
        projectBtn.setOnMousePressed(e -> projectBtn.setStyle(projectBtn.getStyle().replace("#7f49b4", "#68399a")));
        projectBtn.setOnMouseReleased(e -> projectBtn.setStyle(projectBtn.getStyle().replace("#68399a", "#7f49b4")));
        projectList.getChildren().add(projectBtn);
    }

    private void setup() {
        // левая панель 
        root.setPadding(new Insets(10));
        root.setMinWidth(220);
        root.setStyle("""
            -fx-background-color: #141414;
            -fx-background-radius: 10;
        """);

        // тайтл левой панели
        projectLabel.setStyle("""
            -fx-text-fill: #cfcfcf;
            -fx-font-size: 22px;
            -fx-font-weight: bold;
        """);
        
        // VBox контейнер объектов проектов
        projectList.setMinWidth(200);
        projectList.setPadding(new Insets(5, 10, 5, 10));
        projectList.setStyle("-fx-background-color: #141414; ");

        // кнопка "добавить проект"
        projectButton.setMaxWidth(Double.MAX_VALUE);
        projectButton.setStyle("-fx-font-size: 22px; ");
    }
}
