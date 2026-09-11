package org.ozis.ui.panel;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import org.ozis.ui.UiManager.ActionsHandler;

public class TopPanel {

    private final String btn_style = """
            -fx-background-color: #7f49b4;
            -fx-text-fill: #cfcfcf;
            -fx-font-size: 20px;
            -fx-background-radius: 8px;
        """;

    private final String highlighted_btn_style = """
            -fx-background-color: #9a68ca;
            -fx-text-fill: #ffffff;
            -fx-font-size: 20px;
            -fx-background-radius: 8px;
        """;

    private ActionsHandler actionHandler;
    
    private final HBox root = new HBox();
    
    private final Button all_btn = new Button("All");
    private final Button context_btn = new Button("Context");
    private final Button task_btn = new Button("Tasks");
    private final Button decision_btn = new Button("Decisions");

    public TopPanel(ActionsHandler actionHandler) {
        setup();
        this.actionHandler = actionHandler;
        // TODO:
        // назначить каждой кнопке по функции из UiManager
        root.getChildren().addAll(all_btn, context_btn, task_btn, decision_btn);
    }

    public HBox getRoot() {
        return root;
    }

    private void setup() {
        setupActions();
        
        all_btn.setStyle(btn_style);
        context_btn.setStyle(btn_style);
        task_btn.setStyle(btn_style);
        decision_btn.setStyle(btn_style);

        root.setPadding(new Insets(5, 10, 5, 10));
        
        root.setStyle("""
            -fx-background-color: #141414;
            -fx-background-radius: 12px; 
        """);
        root.setMaxWidth(Region.USE_PREF_SIZE);
        root.setMaxHeight(Region.USE_PREF_SIZE);
    }

    private void setupActions() {

        all_btn.setOnAction(event -> {
            System.out.println("Открываю вкладку All");
            actionHandler.tabAction("All");
        });

        context_btn.setOnAction(event -> {
            System.out.println("Открываю вкладку Context");
            actionHandler.tabAction("Context");
        });

        task_btn.setOnAction(event -> {
            System.out.println("Открываю вкладку Tasks");
            actionHandler.tabAction("Task");
        });

        decision_btn.setOnAction(event -> {
            System.out.println("Открываю вкладку Decisions");
            actionHandler.tabAction("Decision");
        });
    }

    public void sethighlightTab(String tabName) {
        setup();
        switch (tabName) {
            case "All":
                all_btn.setStyle(highlighted_btn_style);
                break;
            case "Context":
                context_btn.setStyle(highlighted_btn_style);
                break;
            case "Task":
                task_btn.setStyle(highlighted_btn_style);
                break;
            case "Decision":
                decision_btn.setStyle(highlighted_btn_style);
                break;
            default:
                System.out.println("Error: Попытка изменить стиль вкладки. Неизвестное название.");
                break;
        }
    }
}
