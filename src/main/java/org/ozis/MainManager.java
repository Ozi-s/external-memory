package org.ozis;

import javafx.stage.Stage;
import org.ozis.ui.UiManager;

public class MainManager {

    UiManager uiManager = new UiManager();
    
    public void start(Stage stage) {
        // подготовка данных
        // String[] projects = {"brain-dangeon","external-memory","minecraft-server"};
        
        // инициализация интерфейса
        uiManager.init(stage);
    }
}
