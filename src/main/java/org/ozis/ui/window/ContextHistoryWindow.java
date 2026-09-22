package org.ozis.ui.window;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.ozis.entity.Context;

public class ContextHistoryWindow {

    private final Stage stage = new Stage();
    private final BorderPane root = new BorderPane();

    private final ScrollPane contextsScroll = new ScrollPane();
    private final VBox contextsContainer = new VBox(10);

    private final Button deleteBtn = new Button("Delete");
    private final Button closeBtn = new Button("Close");

    private final List<Context> contexts;
    private final Set<Context> selectedContexts = new HashSet<>();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public ContextHistoryWindow(List<Context> contexts) {
        this.contexts = contexts;

        setup();
        setupActions();
    }

    private void setup() {
        contextsContainer.setPadding(new Insets(10));
        contextsContainer.setFillWidth(true);

        for (Context context : contexts) {
            contextsContainer.getChildren().add(createContextCard(context));
        }

        contextsScroll.setContent(contextsContainer);
        contextsScroll.setFitToWidth(true);
        contextsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        contextsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root.setCenter(contextsScroll);
        BorderPane.setMargin(contextsScroll, new Insets(10));

        HBox buttons = new HBox(10, deleteBtn, closeBtn);
        buttons.setPadding(new Insets(10));
        buttons.setAlignment(Pos.CENTER_RIGHT);

        root.setBottom(buttons);

        Scene scene = new Scene(root, 750, 650);

        stage.setTitle("Context History");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(450);
    }

    private VBox createContextCard(Context context) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setMaxWidth(Double.MAX_VALUE);

        Label dateLabel = new Label(
            context.getLast_updated() != null
                ? context.getLast_updated().format(dateFormatter)
                : "—"
        );

        Label difficultyLabel = new Label("Difficulty: " + context.getDifficulty());
        Label projectLabel = new Label("Project: #" + getProjectId(context));

        dateLabel.setStyle("""
            -fx-font-size: 18px;
            -fx-font-weight: bold;
        """);

        VBox summary = new VBox(5, dateLabel, difficultyLabel, projectLabel);
        VBox details = createDetails(context);

        details.setVisible(false);
        details.setManaged(false);

        card.getChildren().addAll(summary, details);
        updateCardStyle(card, false);

        card.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                details.setVisible(!details.isVisible());
                details.setManaged(!details.isManaged());
                return;
            }

            if (event.getClickCount() == 1) {
                toggleSelection(context, card);
            }
        });

        return card;
    }

    private VBox createDetails(Context context) {
        VBox details = new VBox(10);

        details.getChildren().addAll(
            createTextBlock("To Do", context.getTo_do()),
            createTextBlock("In Progress", context.getIn_progress()),
            createTextBlock("Done", context.getDone()),
            createTextBlock("Think", context.getThink())
        );

        return details;
    }

    private VBox createTextBlock(String title, String text) {
        Label titleLabel = new Label(title);
        titleLabel.setStyle("""
            -fx-font-size: 15px;
            -fx-font-weight: bold;
        """);

        Label textLabel = new Label(
            text == null || text.isBlank() ? "—" : text
        );

        textLabel.setWrapText(true);

        return new VBox(5, titleLabel, textLabel);
    }

    private void toggleSelection(Context context, VBox card) {
        if (selectedContexts.contains(context)) {
            selectedContexts.remove(context);
            updateCardStyle(card, false);
        } else {
            selectedContexts.add(context);
            updateCardStyle(card, true);
        }
    }

    private void updateCardStyle(VBox card, boolean selected) {
        if (selected) {
            card.setStyle("""
                -fx-border-color: #7f49b4;
                -fx-border-width: 2px;
                -fx-border-radius: 8px;
                -fx-background-radius: 8px;
            """);
        } else {
            card.setStyle("""
                -fx-border-color: #888888;
                -fx-border-width: 1px;
                -fx-border-radius: 8px;
                -fx-background-radius: 8px;
            """);
        }
    }

    private int getProjectId(Context context) {
        if (context.getProject_id() == null) {
            return 0;
        }

        return context.getProject_id().getId();
    }

    private void setupActions() {
        deleteBtn.setOnAction(event -> {
            // TODO: удалить selectedContexts
        });

        closeBtn.setOnAction(event -> stage.close());
    }

    public void show() {
        stage.show();
    }
}
