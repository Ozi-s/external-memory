package org.ozis.ui.panel;

import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class TopPanel {

    private final HBox root = new HBox();
    
    private final Button all_btn = new Button("All");
    private final Button context_btn = new Button("Context");
    private final Button tasks_btn = new Button("Tasks");
    private final Button decisions_btn = new Button("Decisions");

    public TopPanel() {
        setup();

        // TODO:
        // назначить каждой кнопке по функции из UiManager
        
        root.getChildren().addAll(all_btn, context_btn, tasks_btn, decisions_btn);
    }

    public HBox getRoot() {
        return root;
    }

    private void setup() {
        String btn_style = """
            -fx-background-color: #7f49b4;
            -fx-text-fill: #cfcfcf;
            -fx-font-size: 20px;
            -fx-background-radius: 8px;
        """;
        
        all_btn.setStyle(btn_style);
        context_btn.setStyle(btn_style);
        tasks_btn.setStyle(btn_style);
        decisions_btn.setStyle(btn_style);

        root.setPadding(new Insets(5, 10, 5, 10));
        
        root.setStyle("""
            -fx-background-color: #141414;
            -fx-background-radius: 12px; 
        """);
        root.setMaxWidth(Region.USE_PREF_SIZE);
        root.setMaxHeight(Region.USE_PREF_SIZE);
    }
}
