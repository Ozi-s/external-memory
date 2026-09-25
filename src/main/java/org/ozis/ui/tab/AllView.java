package org.ozis.ui.tab;

import java.util.List;

import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;

import org.ozis.entity.Context;
import org.ozis.entity.Task;

public class AllView {

    private final ScrollPane taskListScroll = new ScrollPane();
    private final ScrollPane sessionListScroll = new ScrollPane();

    private final GridPane root = new GridPane(); // главный контейнер
    private final VBox taskList = new VBox(5); // блок с прошлыми задачами
    private final VBox sessionList = new VBox(5); // блок с прошедшими сессиями
    private final TextArea contextArea = new TextArea(); // блок с контекстом проекта
    
    // private final ScrollPane activiti_log_s = new ScrollPane();
    // private final BorderPane activiti_root = new BorderPane();
    // private final хз нужно сделать активность как в гитхабе (future...) 

    public AllView(List<Task> tasks, Context context) { // нужно получить информацию об: Tasks (List<Task> tasks); Sessions(List<Session> sessions); Context(Context last_context);
        setup();

        for (Task tsk : tasks) { taskList.getChildren().add(new Label(tsk.getName())); }
        for (int i = 0; i < 15; i++) { sessionList.getChildren().add(new Label("Session # " + i + " on " + i + ".09.2026;")); }
        //contextArea.set();
        
        taskListScroll.setContent(taskList);
        sessionListScroll.setContent(sessionList);
        
        root.add(taskListScroll, 0, 0);
        root.add(sessionListScroll, 1, 0);
        root.add(contextArea, 0, 1);
        GridPane.setColumnSpan(contextArea, 2);
    }

    public GridPane getRoot() { return root; }
    
    private void setup() {
        // rootScroll.setFitToWidth(true);
        // rootScroll.setFitToHeight(true);
        // rootScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        // rootScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        taskListScroll.setFitToWidth(true);
        taskListScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        taskListScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        sessionListScroll.setFitToWidth(true);
        sessionListScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sessionListScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        RowConstraints row1 = new RowConstraints(); row1.setPercentHeight(40);
        RowConstraints row2 = new RowConstraints(); row2.setPercentHeight(60);
        root.getRowConstraints().addAll(row1, row2);
        
        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(50);
        root.getColumnConstraints().addAll(col1, col2);
        
        // activiti_log_s.setFitToWidth(true);
        // activiti_log_s.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        // activiti_log_s.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // TODO:    
        // ебанное оформление цветами и прочей хунёй
    }
}
