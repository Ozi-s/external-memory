package org.ozis.ui.window;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.ozis.entity.Task;

public class TaskViewWindow {

    private final Stage stage = new Stage();
    private final BorderPane root = new BorderPane();

    private final Label nameLabel = new Label();
    private final Label statusLabel = new Label();

    private final Label executionPeriodLabel = new Label();
    private final Label deadlineLabel = new Label();
    private final Label projectIdLabel = new Label();

    private final TextArea descriptionArea = new TextArea();
    private final FlowPane filesPane = new FlowPane(5, 5);

    private final Button editBtn = new Button("Edit");
    private final Button closeBtn = new Button("Close");

    private final Task task;

    public TaskViewWindow(Task task) {
        this.task = task;

        setup();
        setupActions();
    }

    private void setup() {

        // Header

        nameLabel.setText(task.getName());
        nameLabel.setStyle("""
            -fx-font-size: 24px;
            -fx-font-weight: bold;
        """);

        statusLabel.setText(task.getTask_status().getName());
        statusLabel.setStyle("""
            -fx-background-color: #7f49b4;
            -fx-text-fill: #ffffff;
            -fx-padding: 6 12 6 12;
            -fx-background-radius: 8px;
            -fx-font-size: 16px;
        """);

        HBox header = new HBox(15, nameLabel, statusLabel);

        // Metadata

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

        // Description

        Label descriptionTitle = new Label("Description");
        descriptionTitle.setStyle("""
            -fx-font-size: 16px;
            -fx-font-weight: bold;
        """);

        descriptionArea.setText(task.getDescription());
        descriptionArea.setWrapText(true);
        descriptionArea.setEditable(false);
        descriptionArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        VBox descriptionBlock = new VBox(5, descriptionTitle, descriptionArea);
        VBox.setVgrow(descriptionArea, Priority.ALWAYS);

        // Files

        Label filesTitle = new Label("Files");
        filesTitle.setStyle("""
            -fx-font-size: 16px;
            -fx-font-weight: bold;
        """);

        addFiles(task.getFiles());

        VBox filesBlock = new VBox(5, filesTitle, filesPane);

        // Main content

        VBox content = new VBox(20, header, metadata, descriptionBlock, filesBlock);
        content.setPadding(new Insets(20));

        VBox.setVgrow(descriptionBlock, Priority.ALWAYS);

        // Buttons

        HBox buttons = new HBox(10, editBtn, closeBtn);
        buttons.setPadding(new Insets(15, 20, 20, 20));

        // Root

        root.setCenter(content);
        root.setBottom(buttons);

        // Stage

        Scene scene = new Scene(root, 700, 550);

        stage.setTitle("External-Memory - TaskView: " + task.getName());
        stage.setScene(scene);

        stage.setMinWidth(600);
        stage.setMinHeight(450);
    }

    private VBox createMetaBlock(String title, Label value) {
        Label titleLabel = new Label(title);

        titleLabel.setStyle("""
            -fx-text-fill: #888888;
            -fx-font-size: 13px;
        """);

        value.setStyle("""
            -fx-font-size: 16px;
            -fx-font-weight: bold;
        """);

        return new VBox(3, titleLabel, value);
    }

    private void addFiles(String files) {
        if (files == null || files.isBlank()) {
            filesPane.getChildren().add(new Label("No files"));
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

        fileLabel.setStyle("""
            -fx-background-color: #cfcfcf;
            -fx-padding: 6 10 6 10;
            -fx-background-radius: 6px;
        """);

        return fileLabel;
    }

    private void setupActions() {
        editBtn.setOnAction(event -> {
            System.out.println("Редактирование задачи");
        });

        closeBtn.setOnAction(event -> {
            stage.close();
        });
    }

    public void show() {
        stage.show();
    }
}
