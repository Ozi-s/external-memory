package org.ozis.ui;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.scene.layout.Region;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.Priority;

import org.ozis.dao.TaskDao;
import org.ozis.entity.Task;
import org.ozis.entity.Task_Status;
import org.ozis.dao.ContextDao;
import org.ozis.entity.Context;
import org.ozis.dao.ProjectDao;
import org.ozis.dao.TasStaDao;
import org.ozis.entity.Project;

import org.ozis.ui.panel.*;

import org.ozis.ui.tab.*;
import org.ozis.ui.window.AddTaskWindow;
import org.ozis.ui.window.TaskViewWindow;

public class UiManager {

    public String currentTab;
    public Project currentProject;
    private final ActionsHandler actionsHandler = new ActionsHandler();

    private final TaskDao taskDao = new TaskDao(Task.class);
    private final TasStaDao tasStaDao = new TasStaDao(Task_Status.class);
    private final ContextDao contextDao = new ContextDao(Context.class);
    private final ProjectDao projectDao = new ProjectDao(Project.class);

    private final GridPane root = new GridPane(); 
    
    private final LeftPanel leftPanel = new LeftPanel(actionsHandler);
    private final TopPanel topPanel = new TopPanel(actionsHandler);
    private final InfoPanel infoPanel = new InfoPanel();
    
    public void init(Stage stage) {
        root.getChildren().clear();
        setup();

        root.add(leftPanel.getRoot(), 0, 0);
        GridPane.setRowSpan(leftPanel.getRoot(), 2);
        
        Scene scene = new Scene(root);
        stage.setTitle("External-Memory");
        stage.setScene(scene);
        stage.show();
    }

    private void setup() {
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));
    
        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(80);
        
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(Region.USE_PREF_SIZE);
        row1.setPrefHeight(Region.USE_COMPUTED_SIZE);
        row1.setMaxHeight(Region.USE_PREF_SIZE);
        
        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.ALWAYS);
        
        root.getColumnConstraints().addAll(col1, col2);
        root.getRowConstraints().addAll(row1, row2);
        
    }

    public void setCurrentTab(String tabName) {
        currentTab = tabName;
        topPanel.sethighlightTab(tabName);
        System.out.println("Текущая вкладка изменена на: " + tabName);
    }

    
    public class ActionsHandler {

        public void projectAction(String projectName) {
            if (projectName == null) { return; }
            root.getChildren().clear();
            List<Project> projects = projectDao.findByName(projectName);
            Project selectedProject = projects.get(0);
            currentProject = selectedProject;
            AllView allView = new AllView(taskDao.findTaskByPoject(selectedProject.getId()));
            setCurrentTab("All");

            infoPanel.setContent(allView.getRoot());
            
            GridPane.setHalignment(topPanel.getRoot(), HPos.CENTER);
            root.add(leftPanel.getRoot(), 0, 0);
            root.add(topPanel.getRoot(), 1, 0);
            root.add(infoPanel.getRoot(), 1, 1);

            // ===================================================================
            
            // ===================================================================
        }

        public void tabAction(String tabName) {
            if (tabName == null) { return; }
            infoPanel.getRoot().getChildren().clear();

            switch (tabName) {
                case "All":
                    setCurrentTab("All");
                    AllView allView = new AllView(taskDao.findTaskByPoject(currentProject.getId()));
                    infoPanel.setContent(allView.getRoot());
                    break;
                case "Context":
                    setCurrentTab("Context");
                    ContextView contextView = new ContextView(contextDao.findLastContextByProject(currentProject.getName()).get(0));
                    infoPanel.setContent(contextView.getRoot());
                    break;
                case "Task":
                    setCurrentTab("Task");
                    TaskView taskView = new TaskView(tasStaDao.findAll(), taskDao.findTaskByPoject(currentProject.getId()), actionsHandler);
                    infoPanel.setContent(taskView.getRoot());
                    break;
                case "Decision":
                    setCurrentTab("Decision");
                    Map<String, List<String>> map = new HashMap<>();
                    List<String> list = List.of("decision_123.md", "decision_321.md", "decision_213.md", "decision_231.md");
                    map.put("Ui", list);
                    map.put("Core", list);
                    map.put("Architecture", list);
                    DecisionView decisionView = new DecisionView(map);
                    infoPanel.setContent(decisionView.getRoot());
                    break;
                default:
                    System.out.println("Error: Ошибка действия вкладки.");
                    break;
            }
        }
    
        public void addTaskAction(String statusName) {
            AddTaskWindow taskWindow = new AddTaskWindow(taskDao, tasStaDao.findAll(), tasStaDao.findByName(statusName).get(0), currentProject);
            System.out.println("Открываю окно добавления задачи");
            taskWindow.show();
        }
        public void viewTaskAction(Task task) {
            TaskViewWindow taskViewWindow = new TaskViewWindow(task);
            System.out.println("Открываю окно просмотра задачи");
            taskViewWindow.show();
        }
    }
}
