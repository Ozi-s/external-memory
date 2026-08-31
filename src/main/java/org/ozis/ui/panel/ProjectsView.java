package org.ozis.ui.panel;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import org.ozis.dao.ProjectDao;
import org.ozis.entity.Project;


public class ProjectsView {
    private static void refreshProjects() {
        System.out.println("refreshProjects вызван");
        projects.getChildren().clear();
        List<Project> allProjects = projectDao.findAll();
        System.out.println("Найдено проектов: " + allProjects.size());
        for (Project p : allProjects) {
            System.out.println("Добавляем проект: " + p.getName());
            show_new_project(p.getName());
        }
    }
    static List<String> buffOfProjects = new ArrayList<>();

    static BorderPane LeftPanel = new BorderPane(); // левая панель
    static Label projectsTitle = new Label("Проекты:"); // Тайтл сверху
    static VBox projects = new VBox(10); // Лист проектов
    static Button projectAddBtn = new Button("+"); // кнопка добавить проект
    static ProjectDao projectDao=new ProjectDao(Project.class);
    public static BorderPane getLeftPanel() {
        // левая панель 
        LeftPanel.setPadding(new Insets(20));
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
        
        // VBox контейнер объектов проектов
        projects.setMinWidth(200);
        projects.setPadding(new Insets(5, 10, 5, 10));
        projects.setStyle("-fx-background-color: #141414; ");

        // кнопка "добавить проект"

        projectAddBtn.setMaxWidth(Double.MAX_VALUE);
        projectAddBtn.setStyle("-fx-font-size: 22px; ");
        projectAddBtn.setOnAction(event -> {
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



        LeftPanel.setPadding(new Insets(10));
        LeftPanel.setTop(projectsTitle);
        LeftPanel.setCenter(projects);
        LeftPanel.setBottom(projectAddBtn);
        
        return LeftPanel;
    }

    // добавить проект в поле проектов
    public static void show_new_project(String project_name) {
        Button projectBtn = new Button(project_name);
        projectBtn.setMaxWidth(Double.MAX_VALUE);
        projectBtn.setStyle("""
            -fx-background-color: #7f49b4;
            -fx-text-fill: #cfcfcf;
            -fx-font-size: 20px;
        """);
        projectBtn.setOnMousePressed(e -> projectBtn.setStyle(projectBtn.getStyle().replace("#7f49b4", "#68399a")));
        projectBtn.setOnMouseReleased(e -> projectBtn.setStyle(projectBtn.getStyle().replace("#68399a", "#7f49b4")));
        projects.getChildren().add(projectBtn);
    }
    public static void hide_project() {}
}
