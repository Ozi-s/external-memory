package org.ozis.ui.tab;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import org.ozis.entity.Context;
import org.ozis.entity.Project;
import org.ozis.entity.WorkSession;
import org.ozis.ui.ActionManager;

public class ContextView {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd.MM");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    private final GridPane root = new GridPane();
    private final ActionManager actionManager;
    private final Project currentProject;
    private final Context context;

    // Left
    private final VBox contextBlock = new VBox(20);
    private final ScrollPane contextScroll = new ScrollPane();

    // Right
    private final VBox sessionBlock = new VBox(15);

    public ContextView(Context context, Project currentProject, ActionManager actionManager) {
        this.context = context;
        this.currentProject = currentProject;
        this.actionManager = actionManager;

        setup();
        buildContext();
        buildSessions();
    }

    public GridPane getRoot() {
        return root;
    }

    // ==== Настройка ====

    private void setup() {
        root.setHgap(15);
        root.setPadding(new Insets(20));
        root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.ALWAYS);
        root.getRowConstraints().add(row);  

        ColumnConstraints leftCol = new ColumnConstraints();
        leftCol.setPercentWidth(65);
        ColumnConstraints rightCol = new ColumnConstraints();
        rightCol.setPercentWidth(35);
        root.getColumnConstraints().addAll(leftCol, rightCol);

        // ==== Left: Context ====

        Label contextTitle = new Label("Context");
        contextTitle.getStyleClass().add("section-title");

        contextBlock.setPadding(new Insets(15));
        contextScroll.setContent(contextBlock);
        contextScroll.setFitToWidth(true);
        contextScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        contextScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        contextScroll.getStyleClass().add("context-panel");

        GridPane.setHgrow(contextScroll, Priority.ALWAYS);
        GridPane.setVgrow(contextScroll, Priority.ALWAYS);

        Button seePrevBtn = new Button("See previous");
        Button editBtn = new Button("Edit");
        Button addBtn = new Button("Add new");
        seePrevBtn.getStyleClass().add("btn");
        editBtn.getStyleClass().add("btn");
        addBtn.getStyleClass().add("btn");

        seePrevBtn.setOnAction(e -> actionManager.showContextHistory());
        editBtn.setOnAction(e -> System.out.println("Edit context"));    
        addBtn.setOnAction(e -> System.out.println("Add new context"));

        HBox contextButtons = new HBox(10, seePrevBtn, editBtn, addBtn);
        contextButtons.setAlignment(Pos.CENTER_RIGHT);

        VBox leftColumn = new VBox(10, contextTitle, contextScroll, contextButtons);
        VBox.setVgrow(contextScroll, Priority.ALWAYS);
        leftColumn.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(leftColumn, Priority.ALWAYS);
        
        root.add(leftColumn, 0, 0);

        // ==== Right: Sessions ====

        Label sessionsTitle = new Label("Sessions");
        sessionsTitle.getStyleClass().add("section-title");

        VBox rightColumn = new VBox(15, sessionsTitle, sessionBlock);
        VBox.setVgrow(sessionBlock, Priority.ALWAYS);
        rightColumn.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(rightColumn, Priority.ALWAYS);

        root.add(rightColumn, 1, 0);
    }

    // ==== Контекст ====

    private void buildContext() {
        if (context == null) {
            Label empty = new Label("No context yet");
            empty.getStyleClass().add("label-muted");
            contextBlock.getChildren().add(empty);
            return;
        }

        contextBlock.getChildren().addAll(
            createContextSection("TO DO", context.getTo_do()),
            createContextSection("IN PROGRESS", context.getIn_progress()),
            createContextSection("DONE", context.getDone()),
            createContextSection("THINK", context.getThink())
        );
    }

    private VBox createContextSection(String title, String text) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("context-section-title");

        Region divider = new Region();
        divider.getStyleClass().add("context-divider");

        Label textLabel = new Label(text == null || text.isBlank() ? "—" : text);
        textLabel.setWrapText(true);
        textLabel.getStyleClass().add("context-block-text");

        return new VBox(5, titleLabel, divider, textLabel);
    }

    // ==== Сессии ====

    private void buildSessions() {
        VBox current = buildCurrentSection();
        VBox recent = buildRecentSection();
        Button seeAll = buildSeeAllButton();

        VBox.setVgrow(recent, Priority.ALWAYS);

        sessionBlock.getChildren().addAll(current, recent, seeAll);
    }

    private VBox buildCurrentSection() {
        Label titleLabel = new Label("Current");
        titleLabel.getStyleClass().add("section-heading");

        Region divider = new Region();
        divider.getStyleClass().add("context-divider");

        VBox card = new VBox(8);
        card.getStyleClass().add("context-panel");
        card.setPadding(new Insets(12));

        Optional<WorkSession> active = actionManager.getActiveSession();
        if (active.isEmpty()) {
            Label empty = new Label("No active session");
            empty.getStyleClass().add("label-muted");

            Button startBtn = new Button("Start session");
            startBtn.getStyleClass().add("btn");
            startBtn.setMaxWidth(Double.MAX_VALUE);
            startBtn.setOnAction(e -> actionManager.startSession());

            card.getChildren().addAll(empty, startBtn);
        } else {
            WorkSession s = active.get();

            Label started = new Label("Started " + s.getStartTime().format(TIME_FMT));
            started.getStyleClass().add("context-block-text");

            Label focus = new Label("Focus: " + (s.getFocus() != null ? s.getFocus().getName() : "—"));
            focus.getStyleClass().add("label-muted");

            Button endBtn = new Button("End session");
            endBtn.getStyleClass().add("btn-danger");
            endBtn.setMaxWidth(Double.MAX_VALUE);
            endBtn.setOnAction(e -> actionManager.endSession(s));

            card.getChildren().addAll(started, focus, endBtn);
        }

        return new VBox(5, titleLabel, divider, card);
    }

    private VBox buildRecentSection() {
        Label titleLabel = new Label("Recent");
        titleLabel.getStyleClass().add("section-heading");

        Region divider = new Region();
        divider.getStyleClass().add("context-divider");

        VBox list = new VBox(2);
        list.getStyleClass().add("context-panel");
        list.setPadding(new Insets(8));
        VBox.setVgrow(list, Priority.ALWAYS);
        list.setMaxHeight(Double.MAX_VALUE);

        List<WorkSession> recent = actionManager.getRecentSessions(5);
        if (recent.isEmpty()) {
            Label empty = new Label("No sessions yet");
            empty.getStyleClass().add("label-muted");
            list.getChildren().add(empty);
        } else {
            for (WorkSession s : recent) {
                list.getChildren().add(createSessionRow(s));
            }
        }

        return new VBox(5, titleLabel, divider, list);
    }

    private HBox createSessionRow(WorkSession session) {
        Label date = new Label(session.getDate() != null
            ? session.getDate().format(DATE_FMT) : "—");
        date.getStyleClass().add("session-row-date");

        Label focus = new Label(session.getFocus() != null
            ? "#" + session.getFocus().getId() : "");
        focus.getStyleClass().add("session-row-focus");

        String titleText = session.getResult() != null && !session.getResult().isBlank()
            ? session.getResult()
            : (session.getFocus() != null ? session.getFocus().getName() : "Session");

        if (titleText.length() > 30) {
            titleText = titleText.substring(0, 30) + "...";
        }

        Label title = new Label(titleText);
        title.getStyleClass().add("session-row-title");
        HBox.setHgrow(title, Priority.ALWAYS);

        HBox row = new HBox(5, date, focus, title);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("session-row");

        return row;
    }

    private Button buildSeeAllButton() {
        Button seeAllBtn = new Button("See all sessions");
        seeAllBtn.getStyleClass().add("btn");
        seeAllBtn.setMaxWidth(Double.MAX_VALUE);
        seeAllBtn.setOnAction(e -> System.out.println("See all sessions"));

        return seeAllBtn;
    }
}
