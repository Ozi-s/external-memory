package org.ozis.ui.panel;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.Node;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class InfoPanel {

    private final StackPane root = new StackPane();

    public StackPane getRoot() {
        setup();
        return root;
    }

    public void setContent(Node node) {
        root.getChildren().setAll(node);
    }

    private void setup() {
        root.setPadding(new Insets(10));
        root.setMinWidth(220);
        root.setStyle("""
            -fx-background-color: #141414;
            -fx-background-radius: 10;
        """);
    }
}
