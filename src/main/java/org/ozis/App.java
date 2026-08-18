package org.ozis;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public void start(Stage stage) {
        MainManager main = new MainManager();
        main.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
