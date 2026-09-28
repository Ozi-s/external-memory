package org.ozis.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;

import org.ozis.dao.ContextDao;
import org.ozis.dao.ProjectDao;
import org.ozis.dao.TasStaDao;
import org.ozis.dao.TaskDao;
import org.ozis.entity.Context;
import org.ozis.entity.Project;
import org.ozis.entity.Task;
import org.ozis.entity.Task_Status;
import org.ozis.ui.tab.AllView;
import org.ozis.ui.tab.ContextView;
import org.ozis.ui.tab.DecisionView;
import org.ozis.ui.tab.TaskView;
import org.ozis.ui.util.Notifier;
import org.ozis.ui.window.ContextHistoryWindow;
import org.ozis.ui.window.TaskViewWindow;

public class ActionManager {

    private final UiManager uiManager;

    private final TaskDao taskDao = new TaskDao(Task.class);
    private final TasStaDao tasStaDao = new TasStaDao(Task_Status.class);
    private final ContextDao contextDao = new ContextDao(Context.class);
    private final ProjectDao projectDao = new ProjectDao(Project.class);

    private Project currentProject;
    private String currentTab;
    private Context lastContext;

    public ActionManager(UiManager uiManager) {
        this.uiManager = uiManager;
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

    public void openTask(Task task) {
        if (task == null) { return; }
        TaskViewWindow window = new TaskViewWindow(task, this);
        window.show();
    }
    
    public void deleteTasks(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) { return; }
        
        // TODO: когда появится soft delete в БД:
        // for (Task t: tasks) {taskDao.softDelete(task); }
        // uiManager.invalidateView("Task");
        // uiManager.invalidateView("All");
        // switchTab("Task");
    }

    public void showContextHistory() {
        if (currentProject == null) return;

        ContextHistoryWindow window = new ContextHistoryWindow(
            contextDao.findContextByProject(currentProject.getName())
        );
        window.show();
    }

    // ==== Создание вьюх ====

    private Node createView(String tabName) {
        switch (tabName) {
            case "All":
                AllView allView = new AllView(currentProject, taskDao.findTaskByPoject(currentProject.getId()), lastContext, this);
                return allView.getRoot();

            case "Context":
                if (lastContext == null) {
                    return new Label("Контекст не найден для проекта " + currentProject.getName());
                }
                ContextView contextView = new ContextView(lastContext, this);
                return contextView.getRoot();

            case "Task":
                TaskView taskView = new TaskView(tasStaDao, taskDao, currentProject, this);
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

    public boolean confirmAndDelete(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) return false;

        String header = tasks.size() == 1
            ? "Delete \"" + tasks.get(0).getName() + "\"?"
            : "Delete " + tasks.size() + " tasks?";
            
        boolean confirmed = Notifier.confirm("Delete tasks", header, "This action cannot be undone.");
        if (!confirmed) return false;

        deleteTasks(tasks);
        return true;
    }
}
