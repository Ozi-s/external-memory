package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Applications {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new FXMLLoader(this.getClass().getResource("view.fxml")).load();
        stage.setTitle("Dice Roller");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args)
    }
}
