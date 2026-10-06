package org.ozis.ui.window;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.ozis.entity.Project;
import org.ozis.entity.Task;
import org.ozis.entity.Task_Status;
import org.ozis.ui.ActionManager;
import org.ozis.ui.util.Styles;
import org.ozis.util.Ui;

public class AddTaskWindow {

    // ==== DATA ====
    private final Stage stage = new Stage();
    private final BorderPane root = new BorderPane();

    private final ActionManager actionManager;
    private final Project currentProject;

    // ==== Поля формы ====
    private final TextField nameField = new TextField();
    private final ComboBox<String> statusBox = new ComboBox<>();

    private final Spinner<Integer> executionPeriodBox = new Spinner<>(1, Integer.MAX_VALUE, 1);
    private final DatePicker deadlinePicker = new DatePicker();
    private final Label projectIdLabel = new Label();

    private final TextArea descriptionArea = new TextArea();

    private final FlowPane filesPane = new FlowPane(5, 5);
    private final Button addFileBtn = new Button("+");
    private final List<String> files = new ArrayList<>();

    // ==== Кнопки ====
    private final Button saveBtn = new Button("Save");
    private final Button duplicateBtn = new Button("Duplicate");
    private final Button cancelBtn = new Button("Cancel");

    public AddTaskWindow(ActionManager actionManager, List<Task_Status> statusList, Task_Status selectedStatus, Project currentProject) {
        this.actionManager = actionManager;
        this.currentProject = currentProject;

        setup(statusList, selectedStatus);
        setupActions(statusList);
    }

    public void show() { 

        var screen = javafx.stage.Screen.getPrimary();
        System.out.println("OutputScaleX: " + screen.getOutputScaleX());
        System.out.println("OutputScaleY: " + screen.getOutputScaleY());
        System.out.println("Bounds: " + screen.getBounds());
        System.out.println("VisualBounds: " + screen.getVisualBounds());
        
        stage.show();
    }
    public boolean isShowing() { return stage.isShowing(); }
    public void close() { stage.close(); }

    private void setup(List<Task_Status> statusList, Task_Status selectedStatus) {

        // ==== Header ====

        Label windowTitle = new Label("New Task");
        windowTitle.getStyleClass().add("window-title");

        nameField.setPromptText("Task name");
        nameField.getStyleClass().add("window-title-input");

        for (Task_Status s : statusList) { statusBox.getItems().add(s.getName()); }
        if (selectedStatus != null) { statusBox.getSelectionModel().select(selectedStatus.getName()); }
        statusBox.getStyleClass().add("status-badge-combo");

        VBox nameSection = wrapSection("Name", nameField);
        VBox statusSection = wrapSection("Status", statusBox);
        HBox.setHgrow(nameSection, Priority.ALWAYS);
        
        HBox headerRow = new HBox(15, nameSection, statusSection);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        VBox headerBlock = new VBox(10, windowTitle, headerRow);

       // ==== Metadata ====

        projectIdLabel.setText("#" + currentProject.getId());
        projectIdLabel.getStyleClass().add("meta-value");

        executionPeriodBox.setEditable(true);
        executionPeriodBox.getEditor().setTextFormatter(new TextFormatter<>(change -> {
            String text = change.getControlNewText();
            if (text.isEmpty() || text.matches("\\d+")) return change;
            return null;
        }));
        executionPeriodBox.getStyleClass().add("meta-value-input");

        deadlinePicker.getStyleClass().add("meta-value-input");

        GridPane metadata = new GridPane();
        metadata.setHgap(40);
        metadata.setVgap(5);
        metadata.add(createMetaBlock("Execution period", executionPeriodBox), 0, 0);
        metadata.add(createMetaBlock("Deadline", deadlinePicker), 1, 0);
        metadata.add(createMetaBlock("Project", projectIdLabel), 2, 0);

        // ==== Description ====

        Label descriptionTitle = new Label("Description");
        descriptionTitle.getStyleClass().add("section-heading");

        descriptionArea.setPromptText("Description");
        descriptionArea.setWrapText(true);
        descriptionArea.setMaxWidth(Double.MAX_VALUE);
        descriptionArea.setPrefHeight(160);
        descriptionArea.setMinHeight(120);
        descriptionArea.getStyleClass().add("editable-area");

        VBox descriptionBlock = new VBox(5, descriptionTitle, descriptionArea);

        // ==== Files ====

        Label filesTitle = new Label("Files");
        filesTitle.getStyleClass().add("section-heading");

        addFileBtn.setText("+");
        addFileBtn.getStyleClass().add("btn-icon");
        filesPane.getChildren().add(addFileBtn);
        filesPane.getStyleClass().add("files-container");

        VBox filesBlock = new VBox(5, filesTitle, filesPane);

        // ==== Main content ====

        VBox content = new VBox(20, headerBlock, metadata, descriptionBlock, filesBlock);
        content.setPadding(new Insets(20));

        VBox.setVgrow(filesBlock, Priority.ALWAYS);

        // ==== Buttons ====

        saveBtn.getStyleClass().add("btn-success");
        duplicateBtn.getStyleClass().add("btn");
        cancelBtn.getStyleClass().add("btn");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox buttons = new HBox(10, spacer, duplicateBtn, cancelBtn, saveBtn);
        buttons.setPadding(new Insets(15, 20, 20, 20));

        // ==== Root ====

        root.setCenter(content);
        root.setBottom(buttons);

        // ==== Stage ====

        Scene scene = new Scene(root, 720, 600);
        Styles.apply(scene);

        stage.widthProperty().addListener((obs, old, nw) ->
            System.out.println("Width: " + nw));
        stage.heightProperty().addListener((obs, old, nh) ->
            System.out.println("Height: " + nh));

        stage.setTitle("External-Memory - New Task");
        stage.setScene(scene);
        stage.setMinWidth(600 / Ui.scale());
        stage.setMinHeight(540 / Ui.scale());
    }

    // ==== Фабричные методы ====

    private VBox createMetaBlock(String title, Node value) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("meta-title");

        value.getStyleClass().add("meta-value-input");

        return new VBox(3, titleLabel, value);
    }

    private VBox wrapSection(String title, Node content) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("section-heading");

        VBox section = new VBox(5, titleLabel, content);
        return section;
    }

    // ==== Действия ====

    private void setupActions(List<Task_Status> statusList) {
        addFileBtn.setOnAction(e -> onAddFile());

        saveBtn.setOnAction(e -> {
            Task task = buildTaskFromForm(statusList);
            if (task == null) return;
            actionManager.createTask(task);
            stage.close();
        });

        duplicateBtn.setOnAction(e -> {
            Task task = buildTaskFromForm(statusList);
            if (task == null) return;
            actionManager.createTask(task);
            // окно остаётся открытым
        });

        cancelBtn.setOnAction(e -> stage.close());
    }

    private Task buildTaskFromForm(List<Task_Status> statusList) {
        String name = nameField.getText().trim();
        if (name.isEmpty()) return null;

        Task_Status status = getStatusByName(statusList, statusBox.getSelectionModel().getSelectedItem());
        if (status == null) return null;

        return new Task(
            name,
            executionPeriodBox.getValue(),
            status,
            String.join(";", files),
            descriptionArea.getText(),
            deadlinePicker.getValue(),
            currentProject
        );
    }

    // ==== Files ====

    private void onAddFile() {
        for (Node node : filesPane.getChildren()) {
            if (node instanceof TextField existing) {
                existing.requestFocus();
                return;
            }
        }

        TextField fileField = new TextField();
        fileField.setPromptText("File name");
        fileField.getStyleClass().add("file-input");

        int buttonIndex = filesPane.getChildren().indexOf(addFileBtn);
        filesPane.getChildren().add(buttonIndex, fileField);
        fileField.requestFocus();

        fileField.setOnAction(e -> {
            String fileName = fileField.getText().trim();
            if (fileName.isEmpty()) {
                filesPane.getChildren().remove(fileField);
                return;
            }

            if (files.contains(fileName)) {
                filesPane.getChildren().remove(fileField);
                return;
            }

            HBox fileItem = createFileItem(fileName);
            files.add(fileName);

            int index = filesPane.getChildren().indexOf(fileField);
            filesPane.getChildren().set(index, fileItem);
        });
    }

    private HBox createFileItem(String fileName) {
        Label fileLabel = new Label(fileName);
        fileLabel.getStyleClass().add("file-item-label");

        Button removeButton = new Button("×");
        removeButton.getStyleClass().add("file-item-remove");
        removeButton.setOnAction(e -> {
            filesPane.getChildren().remove(removeButton.getParent());
            files.remove(fileName);
        });

        HBox fileItem = new HBox(2, fileLabel, removeButton);
        fileItem.setAlignment(Pos.CENTER_LEFT);
        fileItem.getStyleClass().add("file-item");
        return fileItem;
    }

    // ==== Утилиты ====

    private Task_Status getStatusByName(List<Task_Status> statusList, String statusName) {
        if (statusName == null) return null;
        for (Task_Status status : statusList) {
            if (status.getName().equals(statusName)) return status;
        }
        return null;
    }
}
