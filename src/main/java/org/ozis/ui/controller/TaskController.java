package org.ozis.ui.controller;

import java.util.List;

import org.ozis.dao.TasStaDao;
import org.ozis.dao.TaskDao;
import org.ozis.entity.Project;
import org.ozis.entity.Task;
import org.ozis.entity.Task_Status;
import org.ozis.ui.UiManager;
import org.ozis.ui.util.Notifier;
import org.ozis.ui.window.TaskViewWindow;

public class TaskController {

    private final UiManager uiManager;

    private final TaskDao taskDao = new TaskDao(Task.class);
    private final TasStaDao tasStaDao = new TasStaDao(Task_Status.class);

    public TaskController(UiManager uiManager) {
        this.uiManager = uiManager;
    }

    // ==== Чтение ====

    public List<Task> getTasks(Project project) {
        if (project == null) return List.of();
        return taskDao.findTaskByPoject(project.getId());
    }

    public List<Task_Status> getAllStatuses() {
        return tasStaDao.findAll();
    }

    public Task_Status getStatusByName(String name) {
        List<Task_Status> list = tasStaDao.findByName(name);
        return list.isEmpty() ? null : list.get(0);
    }

    // ==== Действия ====

    public void createTask(Task task) {
        if (task == null) return;
        taskDao.save(task);
        uiManager.refreshView("Task");
        uiManager.invalidateView("All");
    }

    public boolean deleteTasks(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) return false;

        boolean allOk = true;
        for (Task t : tasks) {
            if (!taskDao.softDelete(t.getId())) {
                allOk = false;
            }
        }

        uiManager.refreshView("Task");
        uiManager.invalidateView("All");
        return allOk;
    }

    public boolean confirmAndDelete(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) return false;

        String header = tasks.size() == 1
            ? "Delete \"" + tasks.get(0).getName() + "\"?"
            : "Delete " + tasks.size() + " tasks?";

        if (!Notifier.confirm("Delete tasks", header, "This action cannot be undone.")) {
            return false;
        }

        return deleteTasks(tasks);
    }

    public void openTask(Task task) {
        if (task == null) return;
        TaskViewWindow window = new TaskViewWindow(task, this);
        window.show();
    }
}
