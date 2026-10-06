package org.ozis.ui.window;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import org.ozis.entity.Task;
import org.ozis.ui.ActionManager;
import org.ozis.ui.util.Styles;

public class TaskViewWindow {

    // ==== DATA ====
    private final Stage stage = new Stage();
    private final BorderPane root = new BorderPane();

    private final Task task;
    private final ActionManager actionManager;

    // ==== Поля формы ====
    private final Label nameLabel = new Label();
    private final Label statusLabel = new Label();

    private final Label executionPeriodLabel = new Label();
    private final Label deadlineLabel = new Label();
    private final Label projectIdLabel = new Label();

    private final TextArea descriptionArea = new TextArea();
    private final FlowPane filesPane = new FlowPane(5, 5);

    // ==== Кнопки ====
    private final Button editBtn = new Button("Edit");
    private final Button deleteBtn = new Button("Delete");
    private final Button closeBtn = new Button("Close");

    public TaskViewWindow(Task task, ActionManager actionManager) {
        this.task = task;
        this.actionManager = actionManager;

        setup();
        setupActions();
    }

    public void show() { stage.show(); }
    public boolean isShowing() { return stage.isShowing(); }
    public void close() { stage.close(); }
    
    private void setup() {

        // ==== Header ====

        nameLabel.setText(task.getName());
        nameLabel.getStyleClass().add("window-title");

        statusLabel.setText(task.getTask_status().getName());
        statusLabel.getStyleClass().add("status-badge");

        HBox header = new HBox(15, nameLabel, statusLabel);
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // ==== Metadata ====

        executionPeriodLabel.setText(task.getExecution_period() + " days");

        if (task.getDeadline() != null) {
            deadlineLabel.setText(task.getDeadline().toString());
        } else {
            deadlineLabel.setText("—");
        }

        projectIdLabel.setText("#" + task.getProject_id().getId());

        GridPane metadata = new GridPane();
        metadata.setHgap(40);
        metadata.setVgap(5);

        metadata.add(createMetaBlock("Execution period", executionPeriodLabel), 0, 0);
        metadata.add(createMetaBlock("Deadline", deadlineLabel), 1, 0);
        metadata.add(createMetaBlock("Project", projectIdLabel), 2, 0);

        // ==== Description ====

        Label descriptionTitle = new Label("Description");
        descriptionTitle.getStyleClass().add("section-heading");

        descriptionArea.setText(task.getDescription());
        descriptionArea.setWrapText(true);
        descriptionArea.setEditable(false);
        descriptionArea.setMaxWidth(Double.MAX_VALUE);
        descriptionArea.setPrefHeight(160);
        descriptionArea.setMinHeight(120);
        descriptionArea.getStyleClass().add("readonly-area");

        VBox descriptionBlock = new VBox(5, descriptionTitle, descriptionArea);

        // ==== Files ====

        Label filesTitle = new Label("Files");
        filesTitle.getStyleClass().add("section-heading");

        addFiles(task.getFiles());

        VBox filesBlock = new VBox(5, filesTitle, filesPane);

        // ==== Main content ====

        VBox content = new VBox(20, header, metadata, descriptionBlock, filesBlock);
        content.setPadding(new Insets(20));

        VBox.setVgrow(filesBlock, Priority.ALWAYS);

        // ==== Buttons ====

        editBtn.getStyleClass().add("btn");
        deleteBtn.getStyleClass().add("btn-danger");
        closeBtn.getStyleClass().add("btn");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox buttons = new HBox(10, deleteBtn, spacer, editBtn, closeBtn);
        buttons.setPadding(new Insets(15, 20, 20, 20));

        // ==== Root ====

        root.setCenter(content);
        root.setBottom(buttons);

        // ==== Stage ====

        Scene scene = new Scene(root, 720, 500);
        Styles.apply(scene);

        stage.setTitle("External-Memory - TaskView: " + task.getName());
        stage.setScene(scene);

        // NOTE: 
        // stage.widthProperty().addListener((obs, old, nw) -> System.out.println("Width: " + nw));
        // stage.heightProperty().addListener((obs, old, nh) -> System.out.println("Height: " + nh));

        stage.setMinWidth(600);
        stage.setMinHeight(450);
    }

    private VBox createMetaBlock(String title, Label value) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("meta-title");

        value.getStyleClass().add("meta-value");

        return new VBox(3, titleLabel, value);
    }

    private void addFiles(String files) {
        if (files == null || files.isBlank()) {
            Label empty = new Label("No files");
            empty.getStyleClass().add("label-muted");
            filesPane.getChildren().add(empty);
            return;
        }

        String[] fileNames = files.split("\\s*;\\s*");

        for (String fileName : fileNames) {
            if (!fileName.isBlank()) {
                filesPane.getChildren().add(createFileLabel(fileName));
            }
        }
    }

    private Label createFileLabel(String fileName) {
        Label fileLabel = new Label(fileName);
        fileLabel.getStyleClass().add("chip");
        return fileLabel;
    }

    private void setupActions() {
        editBtn.setOnAction(event -> {
            System.out.println("Редактирование задачи");
        });

        deleteBtn.setOnAction(event -> {
            actionManager.confirmAndDelete(List.of(task));
            // TODO: перекрасить окно в серый, изменить статус задачи, ПОКАЗАТЬ ЧТО МЫ ЕЁ УДАЛИЛИ
        });

        closeBtn.setOnAction(event -> {
            stage.close();
        });
    }

}
