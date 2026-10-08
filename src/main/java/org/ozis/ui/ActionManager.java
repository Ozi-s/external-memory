package org.ozis.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.Label;

import org.ozis.dao.ContextDao;
import org.ozis.dao.ProjectDao;
import org.ozis.dao.TasStaDao;
import org.ozis.dao.TaskDao;
import org.ozis.dao.WorkSessionDao;
import org.ozis.entity.Context;
import org.ozis.entity.Project;
import org.ozis.entity.Task;
import org.ozis.entity.Task_Status;
import org.ozis.entity.WorkSession;
import org.ozis.ui.controller.TaskController;
import org.ozis.ui.tab.AllView;
import org.ozis.ui.tab.ContextView;
import org.ozis.ui.tab.DecisionView;
import org.ozis.ui.tab.TaskView;
import org.ozis.ui.util.Notifier;
import org.ozis.ui.window.ContextHistoryWindow;
import org.ozis.ui.window.TaskViewWindow;

public class ActionManager {

    private final UiManager uiManager;
    private final TaskController taskController;

    private final ContextDao contextDao = new ContextDao(Context.class);
    private final ProjectDao projectDao = new ProjectDao(Project.class);
    private final WorkSessionDao workSessionDao = new WorkSessionDao();

    private Project currentProject;
    private String currentTab;
    private Context lastContext;

    public ActionManager(UiManager uiManager, TaskController taskController) {
        this.uiManager = uiManager;
        this.taskController = taskController;
    }

    // ==== Действия ====

    public void openProject(String projectName) {
        if (projectName == null) return;

        List<Project> projects = projectDao.findByName(projectName);
        if (projects.isEmpty()) return;

        currentProject = projects.get(0);
        refreshLastContext();

        uiManager.invalidateCache();
        uiManager.showProjectLayout();
        switchTab("All");
    }

    public void switchTab(String tabName) {
        if (tabName == null || currentProject == null) return;

        currentTab = tabName;
        uiManager.showView(tabName, () -> createView(tabName));
    }

    public void showContextHistory() {
        if (currentProject == null) return;

        ContextHistoryWindow window = new ContextHistoryWindow(
            contextDao.findContextByProject(currentProject.getName())
        );
        window.show();
    }

    public List<WorkSession> getRecentSessions(int limit) {
        if (currentProject == null) return List.of();
        return workSessionDao.findSesionByProject(currentProject)
            .stream()
            .limit(limit)
            .toList();
    }

    public Optional<WorkSession> getActiveSession() {
        return workSessionDao.findActiveSession();
    }
    
    public void startSession(Task focus) {
        if (currentProject == null) return;

        workSessionDao.startSession(currentProject, focus);
        uiManager.invalidateView("Context");
    }

    public void endSession(WorkSession session) {
        if (session == null) return;
        workSessionDao.finishSession(session.getId());
        uiManager.invalidateView("Context");
        switchTab("Context");
    }

    public void setFocus(Task task) {
        if (task == null) return;

        Optional<WorkSession> active = workSessionDao.findActiveSession();
        if (active.isEmpty()) return;

        WorkSession session = active.get();
        session.setFocus(task);
        workSessionDao.update(session);

        uiManager.invalidateView("Context");
    }

    // ==== Создание вьюх ====

    private Node createView(String tabName) {
        switch (tabName) {
            case "All":
                AllView allView = new AllView(currentProject, lastContext, taskController);
                return allView.getRoot();

            case "Context":
                if (lastContext == null) {
                    return new Label("Контекст не найден для проекта " + currentProject.getName());
                }
                ContextView contextView = new ContextView(lastContext, currentProject, this);
                return contextView.getRoot();

            case "Task":
                TaskView taskView = new TaskView(currentProject, taskController, this);
                return taskView.getRoot();

            case "Decision":
                DecisionView decisionView = new DecisionView(dummyDecisionTree());
                return decisionView.getRoot();

            default:
                System.err.println("Unknown tab: " + tabName);
                return new Label("Unknown tab: " + tabName);
        }
    }


    // ==== Утилиты ====
    
    private Map<String, List<String>> dummyDecisionTree() {
        List<String> list = List.of("decision_123.md", "decision_321.md", "decision_213.md", "decision_231.md");
        Map<String, List<String>> map = new HashMap<>();
        map.put("Ui", list);
        map.put("Core", list);
        map.put("Architecture", list);
        return map;
    }

    private void refreshLastContext() {
        List<Context> contexts = contextDao.findLastContextByProject(currentProject.getName());
        lastContext = contexts.isEmpty() ? null : contexts.get(0);
    }
}
