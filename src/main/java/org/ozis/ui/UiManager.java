package org.ozis.ui;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

import org.ozis.dao.TaskDao;
import org.ozis.entity.Task;
import org.ozis.dao.ContextDao;
import org.ozis.entity.Context;
import org.ozis.ui.panel.LeftPanel;
import org.ozis.ui.panel.TopPanel;
import org.ozis.ui.panel.InfoPanel;

public class UiManager {

    //rivate String currentTab;

    private final TaskDao taskDao = new TaskDao(Task.class);
    private final ContextDao contextDao = new ContextDao(Context.class);

    private final GridPane root = new GridPane(); 
    private final LeftPanel leftPanel = new LeftPanel();
    private final TopPanel topPanel = new TopPanel();
    private final InfoPanel infoPanel = new InfoPanel();
    
    public void init(Stage stage) {
        setup();

        root.add(leftPanel.getRoot(), 0, 0);
        root.add(topPanel.getRoot(), 1, 0);
        root.add(infoPanel.getRoot(), 1, 1);
        GridPane.setRowSpan(leftPanel.getRoot(), 2);
        
        Scene scene = new Scene(root);
        stage.setTitle("External-Memory");
        stage.setScene(scene);
        stage.show();
    }

    private void setup() {
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));
    
        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(70);
        RowConstraints row1 = new RowConstraints(); row1.setPercentHeight(15);
        RowConstraints row2 = new RowConstraints(); row2.setPercentHeight(85);

        root.getColumnConstraints().addAll(col1, col2);
        root.getRowConstraints().addAll(row1, row2);
        
    }
}
