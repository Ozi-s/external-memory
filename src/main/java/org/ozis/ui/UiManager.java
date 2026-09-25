package org.ozis.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import org.ozis.ui.panel.InfoPanel;
import org.ozis.ui.panel.LeftPanel;
import org.ozis.ui.panel.TopPanel;
import org.ozis.ui.util.Styles;

public class UiManager {

    private final GridPane root = new GridPane();

    private final Map<String, Node> viewCache = new HashMap<>();

    private LeftPanel leftPanel;
    private TopPanel topPanel;
    private final InfoPanel infoPanel = new InfoPanel();

    public void init(Stage stage, ActionManager actionManager) {
        leftPanel = new LeftPanel(actionManager);
        topPanel = new TopPanel(actionManager);
        
        root.getChildren().clear();
        setup();

        root.add(leftPanel.getRoot(), 0, 0);
        GridPane.setRowSpan(leftPanel.getRoot(), 2);

        Scene scene = new Scene(root);
        Styles.apply(scene);

        stage.setTitle("External-Memory");
        stage.setScene(scene);
        stage.show();
    }

    // ==== API для ActionManager ====

    public void showProjectLayout() {
        root.getChildren().clear();

        GridPane.setHalignment(topPanel.getRoot(), HPos.CENTER);
        root.add(leftPanel.getRoot(), 0, 0);
        GridPane.setRowSpan(leftPanel.getRoot(), 2);
        root.add(topPanel.getRoot(), 1, 0);
        root.add(infoPanel.getRoot(), 1, 1);
    }

    public void showView(String tabName, Supplier<Node> factory) {
        Node view = viewCache.get(tabName);
        if (view == null) {
            view = factory.get();
            viewCache.put(tabName, view);
        }

        infoPanel.getRoot().getChildren().clear();
        infoPanel.setContent(view);
        topPanel.sethighlightTab(tabName);
    }

    public void clearInfoPanel() {
        infoPanel.getRoot().getChildren().clear();
    }

    public void invalidateCache() {
        viewCache.clear();
    }

    // ==== Внутреннее ====

    private void setup() {
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(80);

        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(Region.USE_PREF_SIZE);
        row1.setPrefHeight(Region.USE_COMPUTED_SIZE);
        row1.setMaxHeight(Region.USE_PREF_SIZE);

        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.ALWAYS);

        root.getColumnConstraints().addAll(col1, col2);
        root.getRowConstraints().addAll(row1, row2);
    }
}
