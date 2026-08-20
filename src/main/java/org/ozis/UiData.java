package org.ozis;

import javafx.stage.Stage;

public class UiData {
    private final Stage stage;
    private final String[] projects;

    public UiData(Stage stage, String[] projects) {
        this.stage = stage;
        this.projects = projects;
    }

    public Stage getStage() {return stage; }
    public String[] getProjects() {return projects; }
}
