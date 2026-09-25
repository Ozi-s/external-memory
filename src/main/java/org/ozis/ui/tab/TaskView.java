package org.ozis.ui.tab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Pos;

import org.ozis.dao.TasStaDao;
import org.ozis.dao.TaskDao;
import org.ozis.entity.Project;
import org.ozis.entity.Task;
import org.ozis.entity.Task_Status;
import org.ozis.ui.UiManager.ActionsHandler;
import org.ozis.ui.window.AddTaskWindow;
import org.ozis.ui.window.TaskViewWindow;

public class TaskView {
    
    private final ScrollPane rootScroll = new ScrollPane();

    private final GridPane root = new GridPane();
    private final GridPane taskBoard = new GridPane();
    // private final TODO: "область под логи." 

    // Контейнеры задач для каждого статуса
    private final Map<String, VBox> taskContainers = new HashMap<>();
    private final Map<String, ScrollPane> taskScrolls = new HashMap<>();

    private final ActionsHandler actionsHandler;
    private final List<Task_Status> taskStatusList;
    private final TaskDao taskDao;
    private final TasStaDao tasStaDao;
    private List<Task> taskList;
    
    private List<Task> filterTask;
    private String currentFilter;
    private final Project currentProject;

    private AddTaskWindow addTaskWindow;
    
    public TaskView(TasStaDao tasStaDao, TaskDao taskDao, Project currentProject, ActionsHandler actionsHandler) {
        this.tasStaDao = tasStaDao;
        this.taskStatusList = tasStaDao.findAll();
        this.taskDao = taskDao;
        this.taskList = taskDao.findTaskByPoject(currentProject.getId());
        this.actionsHandler = actionsHandler;
        this.currentProject = currentProject;
        setup();
        
        // Создаём колонки статусов
        createStatusColumns();
        //addTasks(this.taskList);
        setFilter("All");
        
        // Добавляем доску в основной контейнер
        root.add(taskBoard, 0, 0);
        // TODO: добавить область для логов

        // Передаём root в ScrollPane
        rootScroll.setContent(root);
    }

// ##############

    public ScrollPane getRoot() { return rootScroll; }

// ##############

    private void setup() {
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        rootScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        taskBoard.setHgap(10);
        taskBoard.setVgap(10);
        taskBoard.setPadding(new Insets(10));

        taskBoard.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        taskBoard.setStyle("""
            -fx-grid-lines-visible: true;
        """);

        RowConstraints headerRow = new RowConstraints();
        headerRow.setPrefHeight(35);

        RowConstraints taskRow = new RowConstraints();
        taskRow.setVgrow(Priority.ALWAYS);

        RowConstraints addRow = new RowConstraints();
        addRow.setPrefHeight(35);

        taskBoard.getRowConstraints().addAll(
            headerRow,
            taskRow,
            addRow
        );

        GridPane.setHgrow(taskBoard, Priority.ALWAYS);
        GridPane.setVgrow(taskBoard, Priority.ALWAYS);
        
        // RowConstraints boardRow = new RowConstraints(); boardRow.setPercentHeight(70);
        // RowConstraints logRow = new RowConstraints(); logRow.setPercentHeight(30);

        // root.getRowConstraints().addAll(boardRow, logRow);

        // GridPane.setHgrow(log, Priority.ALWAYS);
        // GridPane.setVgrow(log, Priority.ALWAYS);
    }

// ##############

    // Создать столбики для доски
    private void createStatusColumns() {
    
        Button allButton = new Button("All");
        Button dayButton = new Button("Day");
        Button weekButton = new Button("Week");
        Button monthButton = new Button("Month");

        allButton.setOnAction(event -> { setFilter("All"); });
        dayButton.setOnAction(event -> { setFilter("Day"); });
        weekButton.setOnAction(event -> { setFilter("Week"); });
        monthButton.setOnAction(event -> { setFilter("Month"); });
        
        VBox filterBox = new VBox(
            5,
            allButton,
            dayButton,
            weekButton,
            monthButton
        );
        filterBox.setAlignment(Pos.CENTER);
        
        taskBoard.add(filterBox, 0, 1);

        GridPane.setHalignment(filterBox, HPos.CENTER);
        GridPane.setValignment(filterBox, VPos.CENTER);
        
        ColumnConstraints filterColumn = new ColumnConstraints(); filterColumn.setPercentWidth(12);
        taskBoard.getColumnConstraints().add(filterColumn);
        
        for (int i = 0; i < taskStatusList.size(); i++) {
            ColumnConstraints statusColumn = new ColumnConstraints();
            statusColumn.setPercentWidth(88.0 / taskStatusList.size());

            taskBoard.getColumnConstraints().add(statusColumn);
            
            Task_Status status = taskStatusList.get(i);
            int column = i + 1;

            String statusName = status.getName();
            Label statusLabel = new Label(statusName);
            VBox tasksContainer = createTaskContainer();
            ScrollPane tasksContainerArea = createTaskContainerArea(tasksContainer);
            Button addTaskButton = createTaskBtn(statusName);

            // Сохраняем VBox по имени статуса
            taskContainers.put(statusName, tasksContainer);
            taskScrolls.put(statusName, tasksContainerArea);

            // Добавляем заголовок
            taskBoard.add(statusLabel, column, 0);
            GridPane.setHalignment(statusLabel, HPos.CENTER);
            GridPane.setValignment(statusLabel, VPos.CENTER);
            // Добавляем контейнер задач
            taskBoard.add(tasksContainerArea, column, 1);

            GridPane.setHgrow(tasksContainerArea, Priority.ALWAYS);
            GridPane.setVgrow(tasksContainerArea, Priority.ALWAYS);
            
            // Добавляем кнопку
            taskBoard.add(addTaskButton, column, 2);
            GridPane.setHalignment(addTaskButton, HPos.CENTER);
            GridPane.setValignment(addTaskButton, VPos.CENTER);
        }
    }

// ##############

    // Создать контейнер для задач
    private VBox createTaskContainer() {
        VBox container = new VBox(5);
        
        return container;
    }

// ##############

    // Создать контейнер с задачами для доски.
    private ScrollPane createTaskContainerArea(VBox container) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(container);

        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        return scrollPane;
    }

// ##############

    // Установить фильтр для отображаемых задач
    public void setFilter(String filterName) {
        if (filterName == null) { return; }
        currentFilter = filterName;

        taskList = taskDao.findTaskByPoject(currentProject.getId());
        for (Task_Status status : taskStatusList) {
            VBox ct = taskContainers.get(status.getName());
            ct.getChildren().clear();
        }
        switch (filterName) {
            case "All":
                addTasks(taskList);
                break;
            case "Day":
                filterTask = taskList.stream()
                    .filter(task -> task.getExecution_period() <= 1)
                    .toList();
                
                addTasks(filterTask);
                break;
                
            case "Week":
                filterTask = taskList.stream()
                    .filter(task -> task.getExecution_period() <= 7 && task.getExecution_period() > 1)
                    .toList();
                
                addTasks(filterTask);
                break;
                
            case "Month":
                filterTask = taskList.stream()
                    .filter(task -> task.getExecution_period() <= 31 && task.getExecution_period() > 7)
                    .toList();
                
                addTasks(filterTask);
                break;

            default:
                System.out.println("ERROR: Ошибка установки фильтра, не правильное имя фильтра.");
                addTasks(taskList);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка установки фильтра, не правильное имя фильтра.");
                alert.setTitle("External-Memory - Alert filter");
                
                alert.showAndWait();
                break;
        }
    }

// ##############

    // Добавить задачИ в доску
    private void addTasks(List<Task> taskList) {
        for (Task task : taskList) {
            addTask(task);
        }
    }

// ##############

    // Добавить задачу в доску
    public void addTask(Task task) {
        // Получаем статус задачи
        Task_Status status = task.getTask_status();
        String statusName = status.getName();
        
        // Находим VBox этого статуса
        VBox container = taskContainers.get(statusName);

        if (container == null) { return; }

        Button taskButton = new Button(task.getName());
        taskButton.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                // открытие окна просмотра задачи
                viewTaskAction(task);
            }
        });
        container.getChildren().add(taskButton);
    }
// ##############
    // Создать кнопку "Добавить задачу"
    private Button createTaskBtn(String statusName) {
        Button button = new Button("+ Add task");

        button.setOnAction(event -> {
            System.out.println("Создаём задачу со статусом: " + statusName);
            addTaskAction(statusName);
        });

        return button;
    }
// ##############

// ##############
    // получить контенер задач по статусу
    public VBox getStatusContainer(String statusName) {
        return taskContainers.get(statusName);
    }
// ##############

    // получить контейнер списка задач по статусу
    public ScrollPane getStatusContainerArea(String statusName) {
        return taskScrolls.get(statusName);
    }
    
// ##############

    // открыть диалоговое окно для добавления новой задачи
    public void addTaskAction(String statusName) {
        if (addTaskWindow != null && addTaskWindow.isShowing()) {
            System.out.println("Закрываю окно добавления задачи");
            addTaskWindow.close();
        }
        addTaskWindow = new AddTaskWindow(
            taskDao,
            actionsHandler,
            tasStaDao.findAll(), 
            tasStaDao.findByName(statusName).get(0), 
            currentProject,
            this
        );
        
        System.out.println("Открываю окно добавления задачи");
        addTaskWindow.show();
    }

// ##############
        
    public void viewTaskAction(Task task) {
        TaskViewWindow taskViewWindow = new TaskViewWindow(task);
        System.out.println("Открываю окно просмотра задачи");
        taskViewWindow.show();
    }
    
// ##############
}
