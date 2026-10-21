package org.ozis.ui.window;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.ozis.entity.WorkSession;
import org.ozis.ui.controller.SessionController;
import org.ozis.ui.util.Styles;

public class EndSessionWindow {

    private final Stage stage = new Stage();
    private final BorderPane root = new BorderPane();

    private final SessionController sessionController;
    private final WorkSession session;

    private final TextArea resultArea = new TextArea();
    private final TextArea notFinishedArea = new TextArea();
    private final TextArea nextArea = new TextArea();
    private final TextArea openQuestionArea = new TextArea();

    private final Button finishBtn = new Button("Finish");
    private final Button cancelBtn = new Button("Cancel");

    public EndSessionWindow(SessionController sessionController, WorkSession session) {
        this.sessionController = sessionController;
        this.session = session;

        setup();
        setupActions();
    }

    public void show() { stage.show(); }

    private void setup() {
        Label titleLabel = new Label("End Session");
        titleLabel.getStyleClass().add("window-title");

        VBox resultBlock = createTextSection("Result", resultArea, "What was achieved?");
        VBox notFinishedBlock = createTextSection("Not Finished", notFinishedArea, "What's left?");
        VBox nextBlock = createTextSection("Next", nextArea, "What to do next time?");
        VBox questionBlock = createTextSection("Open Question", openQuestionArea, "Open questions?");

        VBox content = new VBox(15, titleLabel, resultBlock, notFinishedBlock, nextBlock, questionBlock);
        content.setPadding(new Insets(20));

        finishBtn.getStyleClass().add("btn-danger");
        cancelBtn.getStyleClass().add("btn");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox buttons = new HBox(10, spacer, cancelBtn, finishBtn);
        buttons.setPadding(new Insets(15, 20, 20, 20));

        root.setCenter(content);
        root.setBottom(buttons);

        Scene scene = new Scene(root, 560, 640);
        Styles.apply(scene);

        stage.setTitle("External-Memory - End Session");
        stage.setScene(scene);
        stage.setMinWidth(480);
        stage.setMinHeight(520);
    }

    private VBox createTextSection(String title, TextArea area, String prompt) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("section-heading");

        area.setPromptText(prompt);
        area.setWrapText(true);
        area.setPrefHeight(80);
        area.setMinHeight(60);
        area.getStyleClass().add("editable-area");

        return new VBox(5, titleLabel, area);
    }

    private void setupActions() {
        finishBtn.setOnAction(e -> {
            sessionController.endSession(
                session,
                resultArea.getText(),
                notFinishedArea.getText(),
                nextArea.getText(),
                openQuestionArea.getText()
            );
            stage.close();
        });

        cancelBtn.setOnAction(e -> stage.close());
    }
}
