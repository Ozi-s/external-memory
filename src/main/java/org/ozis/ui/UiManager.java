package org.ozis.ui;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.geometry.HPos;
import javafx.geometry.Insets;

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
import org.ozis.ui.window.ContextHistoryWindow;

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

    private final Map<String, Node> viewCache = new HashMap<>();
    
    public void init(Stage stage) {
        root.getChildren().clear();
        setup();

        root.add(leftPanel.getRoot(), 0, 0);
        GridPane.setRowSpan(leftPanel.getRoot(), 2);
        
        Scene scene = new Scene(root);
        Styles.apply(scene);
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
    
    public class ActionsHandler {

        // Евент открытия проекта
        public void projectAction(String projectName) {
            if (projectName == null) { return; }
            root.getChildren().clear();
            
            List<Project> projects = projectDao.findByName(projectName);
            if (projects.isEmpty()) return;
            
            currentProject = projects.get(0);
            viewCache.clear();
            
            AllView allView = new AllView(taskDao.findTaskByPoject(currentProject.getId()));
            setCurrentTab("All");
            infoPanel.setContent(allView.getRoot());

            GridPane.setHalignment(topPanel.getRoot(), HPos.CENTER);
            root.add(leftPanel.getRoot(), 0, 0);
            root.add(topPanel.getRoot(), 1, 0);
            root.add(infoPanel.getRoot(), 1, 1);
        }


        // Кнопка "просмотреть прошлые контексты"
        public void viewContextHistoryAction() {
            ContextHistoryWindow contextHistoryWindow = new ContextHistoryWindow(contextDao.findContextByProject(currentProject.getName()));
            System.out.println("Открываю историю Context-ов");
            contextHistoryWindow.show();
        }

        
        // Евент смены вкладки
        public void tabAction(String tabName) {
            if (tabName == null || currentProject == null) { return; }
            infoPanel.getRoot().getChildren().clear();
            setCurrentTab(tabName);
            infoPanel.setContent(getOrCreateView(tabName));
        }

        private Node getOrCreateView(String tabName) {
            return viewCache.computeIfAbsent(tabName, this::createView);
        }

        private Node createView(String tabName) {
            switch (tabName) {
                case "All":
                    AllView allView = new AllView(taskDao.findTaskByPoject(currentProject.getId()));
                    return allView.getRoot();

                case "Context":
                    List<Context> contexts = contextDao.findLastContextByProject(currentProject.getName());
                    if (contexts.isEmpty()) {
                        return new Label("Контекст не найден для проекта " + currentProject.getName());
                    }
                    ContextView contextView = new ContextView(contexts.get(0), actionsHandler);
                    return contextView.getRoot();

                case "Task":
                    TaskView taskView = new TaskView(tasStaDao, taskDao, currentProject, actionsHandler);
                    return taskView.getRoot();

                case "Decision":
                    DecisionView decisionView = new DecisionView(getDecisionMap());
                    return decisionView.getRoot();

                default:
                    System.err.println("Unknown tab: " + tabName);
                    return new Label("Unknown tab: " + tabName);
            }
        }

        public void setCurrentTab(String tabName) {
            currentTab = tabName;
            topPanel.sethighlightTab(tabName);
            System.out.println("Текущая вкладка изменена на: " + tabName);
        }



        // TODO: удалить НАХУЙ
        private Map<String, List<String>> getDecisionMap() {
            Map<String, List<String>> map = new HashMap<>();
            List<String> list = List.of("decision_123.md", "decision_321.md", "decision_213.md", "decision_231.md");
            map.put("Ui", list);
            map.put("Core", list);
            map.put("Architecture", list);
            return map;
        }
    }
}
