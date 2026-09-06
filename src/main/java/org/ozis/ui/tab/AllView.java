package org.ozis.ui.tab;

import java.util.List;

import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

import org.ozis.entity.Task;

public class AllView {

    private final ScrollPane rootScroll = new ScrollPane();
    private final ScrollPane taskListScroll = new ScrollPane();
    private final ScrollPane sessionListScroll = new ScrollPane();

    private final GridPane root = new GridPane(); // главный контейнер
    private final VBox taskList = new VBox(5); // блок с прошлыми задачами
    private final VBox sessionList = new VBox(5); // блок с прошедшими сессиями
    private final TextArea context = new TextArea(); // блок с контекстом проекта
    
    // private final ScrollPane activiti_log_s = new ScrollPane();
    // private final BorderPane activiti_root = new BorderPane();
    // private final хз нужно сделать активность как в гитхабе (future...) 

    public AllView(List<Task> tasks) { // нужно получить информацию об: Tasks (List<Task> tasks); Sessions(List<Session> sessions); Context(Context last_context);
        setup();

        for (Task tsk : tasks) { taskList.getChildren().add(getNewLbl(tsk.getName())); }
        for (int i = 0; i < 15; i++) { sessionList.getChildren().add(getNewLbl("Session # " + i + " on " + i + ".09.2026;")); }
        
        
        taskListScroll.setContent(taskList);
        sessionListScroll.setContent(sessionList);
        
        root.add(taskListScroll, 0, 0);
        root.add(sessionListScroll, 1, 0);
        root.add(context, 0, 1);
        GridPane.setColumnSpan(context, 2);

        rootScroll.setContent(root);
    }

    public ScrollPane getRoot() { return root_s; }
    
    private void setup() {
        rootSroll.setFitToWidth(true);
        rootSroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        rootSroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        taskListScroll.setFitToWidth(true);
        taskListScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        taskListScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        sessionListScroll.setFitToWidth(true);
        sessionListScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sessionListScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        RowConstraints row1 = new RowConstraints(); row1.setPercentHeight(50);
        RowConstraints row2 = new RowConstraints(); row2.setPercentHeight(50);
        root.getRowConstraints().addAll(row1, row2);
        
        // activiti_log_s.setFitToWidth(true);
        // activiti_log_s.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        // activiti_log_s.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // TODO:    
        // ебанное оформление цветами и прочей хунёй
    }

    private Label getNewLbl(String name) {
        Label lbl = new Label(name);
        // lbl.setStyle("""...""");
        return lbl;
    }
}
