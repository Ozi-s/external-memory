package org.ozis;

import javafx.stage.Stage;
import org.ozis.ui.UiManager;
import org.ozis.ui.controller.SessionController;
import org.ozis.ui.controller.TaskController;
import org.ozis.ui.ActionManager;

public class MainManager {
    private UiManager uiManager;
    private ActionManager actionManager;

    public void start(Stage stage) {
        // Инициализация
        uiManager = new UiManager();
        TaskController taskController = new TaskController(uiManager);
        SessionController sessionController = new SessionController(uiManager);
        actionManager = new ActionManager(uiManager, taskController, sessionController);
        
        // запуск
        uiManager.init(stage, actionManager);
    }
}
