package org.ozis.ui.tab;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;

import org.ozis.entity.Context;

public class ContextView {

    private final GridPane root = new GridPane();
    private final VBox btnContainer = new VBox();
    
    private final TextArea contextArea = new TextArea();
    private final Button editBtn = new Button("Edit");
    private final Button addNewContextBtn = new Button("Add new Context");
    private final Button lastContextBtn = new Button("See previous contexts");
    // private final Button startNewSessionBtn = new Button();
    // private final Button lastSessionsBtn = new Button();

    public ContextView(Context context) {
        setup();

        btnContainer.getChildren().addAll(editBtn, addNewContextBtn, lastContextBtn);
        contextArea.setText(
            "To do:\n" + context.getTo_do() +
            "\n\nIn progress:\n" + context.getIn_progress() +
            "\n\nThink:\n" + context.getThink()
        );
        
        root.add(contextArea, 0, 0);
        root.add(btnContainer, 1, 0);
    }

    public GridPane getRoot() {
        return root;
    }

    private void setup() {
        setupActions();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));
    
        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(70);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(30);
        root.getColumnConstraints().addAll(col1, col2);

        contextArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnContainer.setMaxHeight(Double.MAX_VALUE);
        
        btnContainer.setPadding(new Insets(10));
    }

    private void setupActions() {

        editBtn.setOnAction(event -> {
            System.out.println("Редактирование Context");
        });

        addNewContextBtn.setOnAction(event -> {
            System.out.println("Добавление нового Context");
        });

        lastContextBtn.setOnAction(event -> {
            System.out.println("Открываем историю Context");
        });
    }
}
