// package org.ozis.ui.window;

// import java.time.format.DateTimeFormatter;
// import java.util.List;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.GridPane;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.VBox;
// import javafx.stage.Stage;

// import org.ozis.entity.Project;
// import org.ozis.entity.Session;
// import org.ozis.entity.Task;

// public class SessionHistoryWindow {

//     private final Stage stage = new Stage();
//     private final BorderPane root = new BorderPane();

//     private final ScrollPane sessionsScroll = new ScrollPane();
//     private final VBox sessionsContainer = new VBox(10);

//     private final Button closeBtn = new Button("Close");

//     private final List<Session> sessions;

//     private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//     private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

//     public SessionHistoryWindow(List<Session> sessions) {
//         this.sessions = sessions;

//         setup();
//         setupActions();
//     }

//     private void setup() {
//         sessionsContainer.setPadding(new Insets(10));
//         sessionsContainer.setFillWidth(true);

//         for (Session session : sessions) {
//             sessionsContainer.getChildren().add(createSessionCard(session));
//         }

//         sessionsScroll.setContent(sessionsContainer);
//         sessionsScroll.setFitToWidth(true);
//         sessionsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
//         sessionsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

//         root.setCenter(sessionsScroll);
//         BorderPane.setMargin(sessionsScroll, new Insets(10));

//         VBox bottom = new VBox(closeBtn);
//         bottom.setPadding(new Insets(10));
//         bottom.setAlignment(Pos.CENTER_RIGHT);

//         root.setBottom(bottom);

//         Scene scene = new Scene(root, 700, 600);

//         stage.setTitle("External-Memory - Session History");
//         stage.setScene(scene);
//         stage.setMinWidth(550);
//         stage.setMinHeight(400);
//     }

//     private VBox createSessionCard(Session session) {
//         VBox card = new VBox(10);
//         card.setPadding(new Insets(15));
//         card.setMaxWidth(Double.MAX_VALUE);

//         Label dateLabel = new Label(
//             session.getDate() != null ? session.getDate().format(dateFormatter) : "—"
//         );

//         Label timeLabel = new Label(formatTime(session));
//         Label focusLabel = new Label("Focus: " + getFocusName(session));
//         Label activityLabel = new Label("Activity: " + getActivityCount(session));

//         dateLabel.setStyle("""
//             -fx-font-size: 18px;
//             -fx-font-weight: bold;
//         """);

//         timeLabel.setStyle("""
//             -fx-font-size: 14px;
//         """);

//         focusLabel.setStyle("""
//             -fx-font-size: 15px;
//         """);

//         activityLabel.setStyle("""
//             -fx-font-size: 14px;
//         """);

//         VBox summary = new VBox(5, dateLabel, timeLabel, focusLabel, activityLabel);
//         VBox details = createDetails(session);

//         details.setVisible(false);
//         details.setManaged(false);

//         card.getChildren().addAll(summary, details);

//         card.setStyle("""
//             -fx-border-color: #888888;
//             -fx-border-width: 1px;
//             -fx-border-radius: 8px;
//             -fx-background-radius: 8px;
//         """);

//         card.setOnMouseClicked(event -> {
//             if (event.getClickCount() == 2) {
//                 boolean expanded = details.isVisible();
//                 details.setVisible(!expanded);
//                 details.setManaged(!expanded);
//                 event.consume();
//             }
//         });

//         return card;
//     }

//     private VBox createDetails(Session session) {
//         VBox details = new VBox(10);

//         GridPane metadata = new GridPane();
//         metadata.setHgap(30);
//         metadata.setVgap(5);

//         metadata.add(createMetaBlock("ID", String.valueOf(session.getId())), 0, 0);
//         metadata.add(createMetaBlock("Project", "#" + getProjectId(session)), 1, 0);
//         metadata.add(createMetaBlock("Start", formatStart(session)), 0, 1);
//         metadata.add(createMetaBlock("End", formatEnd(session)), 1, 1);
//         metadata.add(createMetaBlock(
//             "Date",
//             session.getDate() != null ? session.getDate().format(dateFormatter) : "—"
//         ), 2, 0);

//         Label focusTitle = new Label("Focus");
//         focusTitle.setStyle("-fx-font-weight: bold;");

//         Label focusValue = new Label(getFocusName(session));

//         VBox focusBlock = new VBox(5, focusTitle, focusValue);

//         Label activityTitle = new Label("Activity");
//         activityTitle.setStyle("-fx-font-weight: bold;");

//         VBox activityList = new VBox(3);

//         if (session.getActivity() == null || session.getActivity().isEmpty()) {
//             activityList.getChildren().add(new Label("No completed tasks"));
//         } else {
//             for (Task task : session.getActivity()) {
//                 activityList.getChildren().add(new Label("• " + task.getName()));
//             }
//         }

//         VBox activityBlock = new VBox(5, activityTitle, activityList);

//         VBox resultBlock = createTextBlock("Result", session.getResult());
//         VBox notFinishedBlock = createTextBlock("Not Finished", session.getNotFinished());
//         VBox nextBlock = createTextBlock("Next", session.getNext());
//         VBox openQuestionBlock = createTextBlock("Open Question", session.getOpenQuestion());

//         details.getChildren().addAll(
//             metadata,
//             focusBlock,
//             activityBlock,
//             resultBlock,
//             notFinishedBlock,
//             nextBlock,
//             openQuestionBlock
//         );

//         return details;
//     }

//     private VBox createMetaBlock(String title, String value) {
//         Label titleLabel = new Label(title);
//         Label valueLabel = new Label(value);

//         titleLabel.setStyle("""
//             -fx-text-fill: #888888;
//             -fx-font-size: 12px;
//         """);

//         valueLabel.setStyle("""
//             -fx-font-size: 15px;
//             -fx-font-weight: bold;
//         """);

//         return new VBox(3, titleLabel, valueLabel);
//     }

//     private VBox createTextBlock(String title, String text) {
//         Label titleLabel = new Label(title);

//         titleLabel.setStyle("""
//             -fx-font-size: 15px;
//             -fx-font-weight: bold;
//         """);

//         Label textLabel = new Label(
//             text == null || text.isBlank() ? "—" : text
//         );

//         textLabel.setWrapText(true);

//         return new VBox(5, titleLabel, textLabel);
//     }

//     private String formatTime(Session session) {
//         String start = session.getStart() != null
//             ? session.getStart().format(timeFormatter)
//             : "—";

//         String end = session.getEnd() != null
//             ? session.getEnd().format(timeFormatter)
//             : "—";

//         return start + " — " + end;
//     }

//     private String formatStart(Session session) {
//         return session.getStart() != null
//             ? session.getStart().format(timeFormatter)
//             : "—";
//     }

//     private String formatEnd(Session session) {
//         return session.getEnd() != null
//             ? session.getEnd().format(timeFormatter)
//             : "—";
//     }

//     private String getFocusName(Session session) {
//         if (session.getFocus() == null) {
//             return "—";
//         }

//         return session.getFocus().getName();
//     }

//     private int getActivityCount(Session session) {
//         if (session.getActivity() == null) {
//             return 0;
//         }

//         return session.getActivity().size();
//     }

//     private String getProjectId(Session session) {
//         Object project = session.getProject_id();

//         if (project instanceof Project projectEntity) {
//             return String.valueOf(projectEntity.getId());
//         }

//         return String.valueOf(project);
//     }

//     private void setupActions() {
//         closeBtn.setOnAction(event -> stage.close());
//     }

//     public void show() {
//         stage.show();
//     }
// }
