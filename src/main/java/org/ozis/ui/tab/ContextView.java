package org.ozis.ui.tab;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;

public class ContextView {

    private final GridPane root = new GridPane();
    private final VBox btnContainer = new VBox();
    
    private final TextArea context = new TextArea();
    private final Button editBtn = new Button("Edit");
    private final Button addNewContextBtn = new Button("Add new Context");
    private final Button lastContextBtn = new Button("See previous contexts");
    // private final Button startNewSessionBtn = new Button();
    // private final Button lastSessionsBtn = new Button();

    public ContextView() {
        setup();

        btnContainer.getChildren().addAll(editBtn, addNewContextBtn, lastContextBtn);
        root.add(context, 0, 0);
        root.add(btnContainer, 1, 0);
    }

    public GridPane getRoot() {
        return root;
    }

    private void setup() {
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));
    
        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(75);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(25);
        root.getColumnConstraints().addAll(col1, col2);

        btnContainer.setPadding(new Insets(10));
    }
}
