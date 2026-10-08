package org.ozis;

import javafx.stage.Stage;
import org.ozis.ui.UiManager;
import org.ozis.ui.controller.TaskController;
import org.ozis.ui.ActionManager;

public class MainManager {
    private UiManager uiManager;
    private ActionManager actionManager;

    public void start(Stage stage) {
        // Инициализация
        uiManager = new UiManager();
        TaskController taskController = new TaskController(uiManager);
        actionManager = new ActionManager(uiManager, taskController);
        
        // запуск
        uiManager.init(stage, actionManager);
    }
}
