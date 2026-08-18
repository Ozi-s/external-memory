package org.ozis;

import javafx.stage.Stage;

public class MainManager {

UiManager ui = new UiManager();
  
    public void start(Stage stage) {
        ui.start(stage);
    }
}
