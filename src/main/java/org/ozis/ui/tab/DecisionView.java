package org.ozis.ui.tab;

import java.util.List;
import java.util.Map;

import javafx.scene.control.TreeView;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import org.ozis.entity.Decision;

public class DecisionView {

    private final GridPane root = new GridPane();
    private final TextArea decisionArea = new TextArea();

    private final TreeItem<String> rootTreeItem = new TreeItem<>("Decisions");
    private TreeView<String> decisionTree = new TreeView<>(rootTreeItem);

    public DecisionView(Map<String, List<String>> treeStructure) {
        setup();
        
        for (Map.Entry<String, List<String>> entry : treeStructure.entrySet()) {
            String category = entry.getKey();
            List<String> decisions = entry.getValue();

            TreeItem<String> categoryItem = new TreeItem<>(category);
            rootTreeItem.getChildren().add(categoryItem);

            for (String decision : decisions) {
                TreeItem<String> decisionItem = new TreeItem<>(decision);
                categoryItem.getChildren().add(decisionItem);
            }
        }

        decisionTree.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldItem, newItem) -> {

            if (newItem != null) {
                System.out.println("Выбрано: " + newItem.getValue());
                // TODO:
                // логика вывода текста файла Decision...
            }

        });
        
        root.add(decisionTree, 0, 0);
        root.add(decisionArea, 1, 0);
    }

    public GridPane getRoot() { return root; }
    
    private void setup() {
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));
        
        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(75);
        root.getColumnConstraints().addAll(col1, col2);

        decisionTree.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        decisionArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        GridPane.setHgrow(decisionTree, Priority.ALWAYS);
        GridPane.setVgrow(decisionTree, Priority.ALWAYS);

        GridPane.setHgrow(decisionArea, Priority.ALWAYS);
        GridPane.setVgrow(decisionArea, Priority.ALWAYS);

        root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }
}
