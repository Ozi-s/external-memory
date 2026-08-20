package org.ozis;

import javafx.stage.Stage;
import org.ozis.ui.UiManager;

public class MainManager {

UiManager ui = new UiManager();
  
    public void start(Stage stage) {
        // подготовка данных
        String[] projects = {"brain-dangeon","external-memory","minecraft-server"};
        
        // инициализация интерфейса
        ui.start(new UiData(stage, projects));
    }
}
