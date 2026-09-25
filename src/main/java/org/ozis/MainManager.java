package org.ozis;

import javafx.stage.Stage;
import org.ozis.ui.UiManager;
import org.ozis.ui.ActionManager;

public class MainManager {
    private UiManager uiManager;
    private ActionManager actionManager;

    public void start(Stage stage) {
        // Инициализация
        uiManager = new UiManager();
        actionManager = new ActionManager(uiManager);
        
        // запуск
        uiManager.init(stage, actionManager);
    }
}
