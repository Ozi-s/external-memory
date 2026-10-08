package org.ozis.ui.tab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Optional;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;


import org.ozis.dao.TasStaDao;
import org.ozis.dao.TaskDao;
import org.ozis.entity.Project;
import org.ozis.entity.Task;
import org.ozis.entity.Task_Status;
import org.ozis.entity.WorkSession;
import org.ozis.ui.ActionManager;
import org.ozis.ui.controller.TaskController;
import org.ozis.ui.util.Refreshable;
import org.ozis.ui.window.AddTaskWindow;
import org.ozis.ui.window.TaskViewWindow;


public class TaskView implements Refreshable {

    private final ScrollPane rootScroll = new ScrollPane();
    private final GridPane root = new GridPane();
    private final GridPane taskBoard = new GridPane();

    // Selection
    private final Set<Task> selectedTasks = new LinkedHashSet<>();
    private final Map<Task, Button> taskButtons = new HashMap<>();
    private final HBox selectionBar = new HBox(10);
    private final Label selectionLabel = new Label();
    private final Button deleteBtn = new Button("Delete selected");
    private final Button focusBtn = new Button("Focus");

    // Контейнеры задач по статусу
    private final Map<String, VBox> taskContainers = new HashMap<>();
    private final Map<String, ScrollPane> taskScrolls = new HashMap<>();

    private final TaskController taskController;
    private final ActionManager actionManager;
    
    private final List<Task_Status> taskStatusList;
    private List<Task> taskList;

    private List<Task> filterTask;
    private String currentFilter;
    private final Project currentProject;

    private AddTaskWindow addTaskWindow;

    public TaskView(Project currentProject, TaskController taskController, ActionManager actionManager) {
        this.taskController = taskController;
        this.actionManager = actionManager;
        this.currentProject = currentProject;
        this.taskStatusList = taskController.getAllStatuses();
        this.taskList = taskController.getTasks(currentProject);

        setup();
        createStatusColumns();
        setFilter("All");

        root.add(taskBoard, 0, 0);
        root.add(selectionBar, 0, 1);
        rootScroll.setContent(root);
    }

    public ScrollPane getRoot() { return rootScroll; }

    // ==== Настройка ====

    private void setup() {
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        rootScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        RowConstraints boardRow = new RowConstraints();
        boardRow.setVgrow(Priority.ALWAYS);

        RowConstraints barRow = new RowConstraints();
        barRow.setMinHeight(Region.USE_PREF_SIZE);

        root.getRowConstraints().addAll(boardRow, barRow);

        taskBoard.setHgap(10);
        taskBoard.setVgap(10);
        taskBoard.setPadding(new Insets(10));
        taskBoard.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        RowConstraints headerRow = new RowConstraints();
        headerRow.setPrefHeight(35);

        RowConstraints taskRow = new RowConstraints();
        taskRow.setVgrow(Priority.ALWAYS);

        RowConstraints addRow = new RowConstraints();
        addRow.setPrefHeight(35);

        taskBoard.getRowConstraints().addAll(headerRow, taskRow, addRow);

        GridPane.setHgrow(taskBoard, Priority.ALWAYS);
        GridPane.setVgrow(taskBoard, Priority.ALWAYS);

        // Selection bar
        selectionLabel.getStyleClass().add("selection-label");
        deleteBtn.getStyleClass().add("btn-danger");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        focusBtn.getStyleClass().add("btn");
        focusBtn.setVisible(false);
        focusBtn.setManaged(false);
        focusBtn.setOnAction(e -> onFocusAction());

        selectionBar.getChildren().addAll(selectionLabel, spacer, focusBtn, deleteBtn);
        selectionBar.setAlignment(Pos.CENTER_LEFT);
        selectionBar.setPadding(new Insets(10, 0, 0, 0));
        selectionBar.getStyleClass().add("selection-bar");
        selectionBar.setVisible(false);
        selectionBar.setManaged(false);

        deleteBtn.setOnAction(e -> {
            if (selectedTasks.isEmpty()) return;
            taskController.confirmAndDelete(new ArrayList<>(selectedTasks));
            clearSelection();
        });

        // Клик по пустому месту доски — снять выделение
        taskBoard.setOnMouseClicked(e -> clearSelection());
    }

    // ==== Создание колонок ====

    private void createStatusColumns() {
        Button allButton = new Button("All");
        Button dayButton = new Button("Day");
        Button weekButton = new Button("Week");
        Button monthButton = new Button("Month");

        allButton.setOnAction(e -> setFilter("All"));
        dayButton.setOnAction(e -> setFilter("Day"));
        weekButton.setOnAction(e -> setFilter("Week"));
        monthButton.setOnAction(e -> setFilter("Month"));

        VBox filterBox = new VBox(5, allButton, dayButton, weekButton, monthButton);
        filterBox.setAlignment(Pos.CENTER);
        taskBoard.add(filterBox, 0, 1);

        GridPane.setHalignment(filterBox, HPos.CENTER);
        GridPane.setValignment(filterBox, VPos.CENTER);

        ColumnConstraints filterColumn = new ColumnConstraints();
        filterColumn.setPercentWidth(12);
        taskBoard.getColumnConstraints().add(filterColumn);

        for (int i = 0; i < taskStatusList.size(); i++) {
            ColumnConstraints statusColumn = new ColumnConstraints();
            statusColumn.setPercentWidth(88.0 / taskStatusList.size());
            taskBoard.getColumnConstraints().add(statusColumn);

            Task_Status status = taskStatusList.get(i);
            int column = i + 1;

            String statusName = status.getName();
            Label statusLabel = new Label(statusName);
            statusLabel.getStyleClass().add("task-column-header");
            VBox tasksContainer = createTaskContainer();
            ScrollPane tasksContainerArea = createTaskContainerArea(tasksContainer);
            Button addTaskButton = createTaskBtn(statusName);

            taskContainers.put(statusName, tasksContainer);
            taskScrolls.put(statusName, tasksContainerArea);

            taskBoard.add(statusLabel, column, 0);
            GridPane.setHalignment(statusLabel, HPos.CENTER);
            GridPane.setValignment(statusLabel, VPos.CENTER);

            taskBoard.add(tasksContainerArea, column, 1);
            GridPane.setHgrow(tasksContainerArea, Priority.ALWAYS);
            GridPane.setVgrow(tasksContainerArea, Priority.ALWAYS);

            taskBoard.add(addTaskButton, column, 2);
            GridPane.setHalignment(addTaskButton, HPos.CENTER);
            GridPane.setValignment(addTaskButton, VPos.CENTER);
        }
    }

    private VBox createTaskContainer() {
        VBox container = new VBox(5);
        return container;
    }

    private ScrollPane createTaskContainerArea(VBox container) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
    }

    // ==== Фильтры ====

    public void setFilter(String filterName) {
        if (filterName == null) return;
        currentFilter = filterName;
        applyFilter(filterName);
    }

    private void applyFilter(String filterName) {
        clearSelection();
        taskButtons.clear();

        taskList = taskController.getTasks(currentProject);
        for (Task_Status status : taskStatusList) {
            VBox ct = taskContainers.get(status.getName());
            if (ct != null) ct.getChildren().clear();
        }

        switch (filterName) {
            case "All" -> addTasks(taskList);
            case "Day" -> addTasks(filter(task -> task.getExecution_period() <= 1));
            case "Week" -> addTasks(filter(task -> task.getExecution_period() <= 7 && task.getExecution_period() > 1));
            case "Month" -> addTasks(filter(task -> task.getExecution_period() <= 31 && task.getExecution_period() > 7));
            default -> {
                System.err.println("Unknown filter: " + filterName);
                addTasks(taskList);
            }
        }
    }

    private List<Task> filter(java.util.function.Predicate<Task> predicate) {
        return taskList.stream().filter(predicate).toList();
    }

    private void addTasks(List<Task> taskList) {
        for (Task task : taskList) {
            addTask(task);
        }
    }

    @Override
    public void refresh() {
        clearSelection();
        taskButtons.clear();
        taskList = taskController.getTasks(currentProject);

        for (Task_Status status : taskStatusList) {
            VBox ct = taskContainers.get(status.getName());
            if (ct != null) ct.getChildren().clear();
        }

        applyFilter(currentFilter);  // тот же фильтр, что был
    }

    // ==== Задачи ====

    public void addTask(Task task) {
        Task_Status status = task.getTask_status();
        String statusName = status.getName();
        VBox container = taskContainers.get(statusName);
        if (container == null) return;

        Button taskButton = new Button(task.getName());
        taskButton.setMaxWidth(Double.MAX_VALUE);
        taskButton.getStyleClass().add("task-card");

        taskButton.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                taskController.openTask(task);
                event.consume();
                return;
            }

            if (event.isControlDown()) {
                toggleSelection(task, taskButton);
            } else {
                clearSelection();
                select(task, taskButton);
            }
            event.consume();
        });

        taskButtons.put(task, taskButton);
        container.getChildren().add(taskButton);
    }

    // ==== Selection ====

    private void select(Task task, Button btn) {
        selectedTasks.add(task);
        if (!btn.getStyleClass().contains("selected")) {
            btn.getStyleClass().add("selected");
        }
        updateSelectionBar();
    }

    private void toggleSelection(Task task, Button btn) {
        if (selectedTasks.contains(task)) {
            selectedTasks.remove(task);
            btn.getStyleClass().remove("selected");
        } else {
            selectedTasks.add(task);
            btn.getStyleClass().add("selected");
        }
        updateSelectionBar();
    }

    public void clearSelection() {
        if (selectedTasks.isEmpty()) return;

        for (Task t : selectedTasks) {
            Button btn = taskButtons.get(t);
            if (btn != null) {
                btn.getStyleClass().remove("selected");
            }
        }
        selectedTasks.clear();
        updateSelectionBar();
    }

    private void updateSelectionBar() {
        if (selectedTasks.isEmpty()) {
            selectionBar.setVisible(false);
            selectionBar.setManaged(false);
            return;
        }

        selectionBar.setVisible(true);
        selectionBar.setManaged(true);
        selectionLabel.setText(selectedTasks.size() + " selected");

        // Кнопка Focus — только если выделена ровно одна задача
        if (selectedTasks.size() == 1) {
            focusBtn.setVisible(true);
            focusBtn.setManaged(true);

            Optional<WorkSession> active = actionManager.getActiveSession();
            if (active.isPresent()) {
                focusBtn.setText("Set as focus");
            } else {
                focusBtn.setText("Start with this");
            }
        } else {
            focusBtn.setVisible(false);
            focusBtn.setManaged(false);
        }
    }

    private void onFocusAction() {
        if (selectedTasks.size() != 1) return;
        Task task = selectedTasks.iterator().next();

        Optional<WorkSession> active = actionManager.getActiveSession();
        if (active.isPresent()) {
            actionManager.setFocus(task);
        } else {
            actionManager.startSession(task);
        }

        // Обновить кнопку — состояние могло измениться
        updateSelectionBar();
    }

    // ==== Кнопка Add task ====

    private Button createTaskBtn(String statusName) {
        Button button = new Button("+ Add task");
        button.setOnAction(e -> addTaskAction(statusName));
        return button;
    }

    public VBox getStatusContainer(String statusName) {
        return taskContainers.get(statusName);
    }

    public ScrollPane getStatusContainerArea(String statusName) {
        return taskScrolls.get(statusName);
    }

    public void addTaskAction(String statusName) {
        if (addTaskWindow != null && addTaskWindow.isShowing()) {
            addTaskWindow.close();
        }
        addTaskWindow = new AddTaskWindow(
            taskController,
            taskController.getAllStatuses(),
            taskController.getStatusByName(statusName),
            currentProject
        );
        addTaskWindow.show();
    }
}
