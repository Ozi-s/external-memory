package org.ozis.ui.tab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
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

import org.ozis.entity.Task;
import org.ozis.entity.Task_Status;

public class TaskView {

    private final ScrollPane rootScroll = new ScrollPane();

    private final GridPane root = new GridPane();
    private final GridPane taskBoard = new GridPane();
    // private final область под логи. 

    // Контейнеры задач для каждого статуса
    private final Map<String, VBox> taskContainers = new HashMap<>();
    private final Map<String, ScrollPane> taskScrolls = new HashMap<>();

    public TaskView(List<Task_Status> taskStatusList, List<Task> taskList) { // нужны Все статусы задач; Все задачи конкретного проекта.
        setup();

        // Создаём колонки статусов
        createStatusColumns(taskStatusList);
        addTasks(taskList);

        // Добавляем доску в основной контейнер
        root.add(taskBoard, 0, 0);
        // TODO:
        // добавить область для логов

        // Передаём root в ScrollPane
        rootScroll.setContent(root);
    }

    public ScrollPane getRoot() { return rootScroll; }

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

    private void createStatusColumns(List<Task_Status> taskStatusList) {

        Button allButton = new Button("All");
        Button dayButton = new Button("Day");
        Button weekButton = new Button("Week");
        Button monthButton = new Button("Month");

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


    private void addTasks(List<Task> taskList) {

        for (Task task : taskList) {
            addTask(task);
        }
    }

    public void addTask(Task task) {
        // Получаем статус задачи
        Task_Status status = task.getTask_status();
        String statusName = status.getName();
        
        // Находим VBox этого статуса
        VBox container = taskContainers.get(statusName);

        if (container == null) { return; }

        Button taskButton = new Button(task.getName());
            container.getChildren().add(taskButton);
    }
    
    private Button createTaskBtn(String statusName) {

        Button button = new Button("+ Add task");

        button.setOnAction(event -> {
            System.out.println(
                "Создаём задачу со статусом: "
                + statusName
            );

            // Здесь позже будет открытие
            // окна создания задачи.
        });

        return button;
    }

    public VBox getStatusContainer(String statusName) {
        return taskContainers.get(statusName);
    }
    public ScrollPane getStatusContainerArea(String statusName) {
        return taskScrolls.get(statusName);
    }
}
