package org.ozis.ui.panel;

import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class TopPanel {

    static HBox btn_container = new HBox();
    
    static Button all_btn = new Button("All");
    static Button context_btn = new Button("Context");
    static Button tasks_btn = new Button("Tasks");
    static Button decisions_btn = new Button("Decisions");

    public static HBox get_btns() {
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

        btn_container.setPadding(new Insets(5, 10, 5, 10));
        btn_container.getChildren().addAll(all_btn, context_btn, tasks_btn, decisions_btn);
        btn_container.setStyle("""
            -fx-background-color: #141414;
            -fx-background-radius: 12px; 
        """);
        btn_container.setMaxWidth(Region.USE_PREF_SIZE);
        btn_container.setMaxHeight(Region.USE_PREF_SIZE);
        
        return btn_container;
    }
}
