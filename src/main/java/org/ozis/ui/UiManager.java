package org.ozis.ui;
import org.ozis.UiData;

public class UiManager {

    public void start(UiData uidata) {
        String[] projects = uidata.getProjects();
        MainWindow.start(uidata.getStage(), projects);
    }
}
