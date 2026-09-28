package org.ozis.ui.tab;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import org.ozis.entity.Context;
import org.ozis.entity.Project;
import org.ozis.entity.Task;
import org.ozis.ui.ActionManager;

public class AllView {

    private final ActionManager actionManager;
    private final GridPane root = new GridPane();

    private final VBox taskList = new VBox(5);
    private final VBox sessionList = new VBox(5);
    private final VBox contextBlock = new VBox(10);

    private final ScrollPane taskScroll = new ScrollPane();
    private final ScrollPane sessionScroll = new ScrollPane();
    private final ScrollPane contextScroll = new ScrollPane();

    public AllView(Project project, List<Task> tasks, Context lastContext, ActionManager actionManager) {
        this.actionManager = actionManager;
        setup();

        buildTaskList(tasks);
        buildSessionList();
        buildContext(lastContext);

        root.add(createSection("Tasks", taskScroll), 0, 0);
        root.add(createSection("Sessions", sessionScroll), 1, 0);

        VBox contextSection = createSection("Context", contextScroll);
        root.add(contextSection, 0, 1);
        GridPane.setColumnSpan(contextSection, 2);
    }

    public Node getRoot() {
        return root;
    }

    // ==== Настройка ====

    private void setup() {
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        RowConstraints row1 = new RowConstraints(); row1.setPercentHeight(40);
        RowConstraints row2 = new RowConstraints(); row2.setPercentHeight(60);
        root.getRowConstraints().addAll(row1, row2);

        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(50);
        root.getColumnConstraints().addAll(col1, col2);

        taskScroll.setContent(taskList);
        sessionScroll.setContent(sessionList);
        contextScroll.setContent(contextBlock);

        for (ScrollPane sp : List.of(taskScroll, sessionScroll, contextScroll)) {
            sp.setFitToWidth(true);
            sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }

        GridPane.setHgrow(taskScroll, Priority.ALWAYS);
        GridPane.setVgrow(taskScroll, Priority.ALWAYS);
        GridPane.setHgrow(sessionScroll, Priority.ALWAYS);
        GridPane.setVgrow(sessionScroll, Priority.ALWAYS);
        GridPane.setHgrow(contextScroll, Priority.ALWAYS);
        GridPane.setVgrow(contextScroll, Priority.ALWAYS);
    }

    // ==== Секции ====

    private VBox createSection(String title, Node content) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("section-title");

        VBox section = new VBox(5, titleLabel, content);
        VBox.setVgrow(content, Priority.ALWAYS);

        return section;
    }

    // ==== Задачи ====

    private void buildTaskList(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            taskList.getChildren().add(createEmptyLabel("No tasks yet"));
            return;
        }

        for (Task task : tasks) {
            taskList.getChildren().add(createTaskLabel(task));
        }
    }

    private Label createTaskLabel(Task task) {
        Label label = new Label(task.getName());
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add("task-card");

        label.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                actionManager.openTask(task);
                return;
            }
        });

        return label;
    }

    // ==== Сессии (заглушка) ====

    private void buildSessionList() {
        // TODO: заменить на реальные сессии когда появится Session entity
        sessionList.getChildren().add(
            createEmptyLabel("Session history is not available yet")
        );
    }

    // ==== Контекст ====

    private void buildContext(Context context) {
        if (context == null) {
            contextBlock.getChildren().add(createEmptyLabel("No context yet"));
            return;
        }

        contextBlock.getChildren().addAll(
            createTextBlock("To Do", context.getTo_do()),
            createTextBlock("In Progress", context.getIn_progress()),
            createTextBlock("Done", context.getDone()),
            createTextBlock("Think", context.getThink())
        );
    }

    private VBox createTextBlock(String title, String text) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("context-block-title");

        Label textLabel = new Label(text == null || text.isBlank() ? "—" : text);
        textLabel.setWrapText(true);
        textLabel.getStyleClass().add("context-block-text");

        return new VBox(3, titleLabel, textLabel);
    }

    // ==== Утилиты ====

    private Label createEmptyLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("label-muted");
        return label;
    }
}
