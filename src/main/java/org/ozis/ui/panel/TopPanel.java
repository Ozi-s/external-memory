package org.ozis.ui.panel;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import org.ozis.ui.ActionManager;

public class TopPanel {

    private final ActionManager actionManager;

    private final HBox root = new HBox();
    private final Map<String, Button> tabButtons = new LinkedHashMap<>();

    public TopPanel(ActionManager actionManager) {
        this.actionManager = actionManager;

        tabButtons.put("All",      new Button("All"));
        tabButtons.put("Context",  new Button("Context"));
        tabButtons.put("Task",     new Button("Tasks"));
        tabButtons.put("Decision", new Button("Decisions"));

        setup();
    }

    public HBox getRoot() { return root; }

    private void setup() {
        root.getStyleClass().add("panel");
        root.setMaxWidth(Region.USE_PREF_SIZE);
        root.setMaxHeight(Region.USE_PREF_SIZE);

        tabButtons.forEach((tabName, btn) -> {
            btn.getStyleClass().add("tab-btn");
            btn.setOnAction(e -> {
                System.out.println("Открываю вкладку " + tabName);
                actionManager.switchTab(tabName);
            });
            root.getChildren().add(btn);
        });
    }

    public void sethighlightTab(String tabName) {
        tabButtons.values().forEach(b -> b.getStyleClass().remove("active"));

        Button active = tabButtons.get(tabName);
        if (active != null) {
            active.getStyleClass().add("active");
        } else {
            System.err.println("Unknown tab: " + tabName);
        }
    }
}
