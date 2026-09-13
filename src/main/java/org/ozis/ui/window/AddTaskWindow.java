package org.ozis.ui.window;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import org.ozis.entity.Task_Status;
import org.ozis.dao.TaskDao;
import org.ozis.entity.Project;
import org.ozis.entity.Task;

public class AddTaskWindow {

    private final Stage stage = new Stage();
    
    private final BorderPane root = new BorderPane();
    private final VBox fieldsContainer = new VBox(10);
    private final VBox btnContainer = new VBox(10);

    private final Label propertiesLabel = new Label("Task properties");
    private final TextField nameField = new TextField();
    private final Label deadlineLabel = new Label("Task deadline");
    private final DatePicker deadlinePicker = new DatePicker();
    private final TextArea descriptionArea = new TextArea();

    private final Label statusLabel = new Label("Task status");
    private final ComboBox<String> statusBox = new ComboBox<>();
    private final Label executionPeriodLabel = new Label("Task execution period");
    private final Spinner<Integer> executionPeriodBox = new Spinner<>(1, Integer.MAX_VALUE, 1);
    
    private final FlowPane filesPane = new FlowPane(5, 5);
    private final Button addFileBtn = new Button("+");
    
    private final Button saveBtn = new Button("save");
    private final Button dublicateBtn = new Button("dublicate");
    private final Button cancelBtn = new Button("cancel");

    private final List<String> files = new ArrayList<>();

    public AddTaskWindow(TaskDao taskDao, List<Task_Status> task_status_list, Task_Status selected_status, Project cProject) {
        setup();
        setupActions(taskDao, task_status_list, cProject);

        for (Task_Status tStatus : task_status_list) {
            statusBox.getItems().add(tStatus.getName());
        }
        statusBox.getSelectionModel().select(selected_status.getName());

        root.setRight(fieldsContainer);
        root.setLeft(btnContainer);
    }

    private void setup() {
        nameField.setPromptText("Task name:");
        descriptionArea.setPromptText("Description:");
        descriptionArea.setWrapText(true);
        
        filesPane.getChildren().add(addFileBtn);

        fieldsContainer.getChildren().addAll(
            propertiesLabel,
            nameField,
            descriptionArea,

            statusLabel,
            statusBox,
            executionPeriodLabel,
            executionPeriodBox,
            deadlineLabel,
            deadlinePicker,

            filesPane
        );
        btnContainer.getChildren().addAll(
            saveBtn,
            dublicateBtn,
            cancelBtn
        );

        btnContainer.setAlignment(Pos.CENTER);

        VBox.setMargin(statusLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(filesPane, new Insets(10, 0, 0, 0));
    
        Scene scene = new Scene(root, 600, 300);
        stage.setTitle("Add Task");
        stage.setScene(scene);
    }

    private void setupActions(TaskDao taskDao, List<Task_Status> statusList, Project cProject) {
        addFileBtn.setOnAction(event -> {
            TextField fileField = new TextField();
            fileField.setPromptText("File name");

            int buttonIndex = filesPane.getChildren().indexOf(addFileBtn);
            filesPane.getChildren().add(buttonIndex, fileField);

            fileField.requestFocus();

            fileField.setOnAction(e -> {
                String fileName = fileField.getText().trim();

                if (fileName.isEmpty()) {
                    return;
                }

                HBox fileItem = createFileItem(fileName);
                files.add(fileName);

                int index = filesPane.getChildren().indexOf(fileField);
                filesPane.getChildren().set(index, fileItem);
            });
        });
        cancelBtn.setOnAction(event -> {
            stage.close();
        });

        saveBtn.setOnAction(event -> {
            Task t = new Task(
                nameField.getText(),
                (int) executionPeriodBox.getValue(),
                getStatusByString(statusList, statusBox.getSelectionModel().getSelectedItem()),
                String.join(";", files),
                descriptionArea.getText(),
                (LocalDate) deadlinePicker.getValue(),
                cProject
            );
            System.out.println("Save new Task: " + t.toString());
            taskDao.save(t);
            
            stage.close();
        });

        dublicateBtn.setOnAction(event -> {
            Task t = new Task(
                nameField.getText(),
                (int) executionPeriodBox.getValue(),
                getStatusByString(statusList, statusBox.getSelectionModel().getSelectedItem()),
                String.join(";", files),
                descriptionArea.getText(),
                (LocalDate) deadlinePicker.getValue(),
                cProject
            );
            System.out.println("Save new Task: " + t.toString());
            taskDao.save(t);
            
        });
    }
    
    private HBox createFileItem(String fileName) {
        Label fileLabel = new Label(fileName);
        Button removeButton = new Button("×");

        removeButton.setOnAction(event -> {
            filesPane.getChildren().remove(
                removeButton.getParent()
            );
        });

        HBox fileItem = new HBox(3, fileLabel, removeButton);

        return fileItem;
    }

    private Task_Status getStatusByString(List<Task_Status> statusList, String statusName) {
        for (Task_Status status : statusList) {
            if (status.getName().equals(statusName)) {
                return status;
            } else {continue;}
        }
        return null;
    }
    
    public void show() {
        stage.show();
    }
}
