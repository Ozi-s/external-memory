package org.ozis.ui.tab;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ContextView {

    private final GridPane root = new GridPane();
    
    private final TextArea context = new TextArea();
    private final Button addNewContextBtn = new Button();
    private final Button editBtn = new Button();
    private final Button lastContextBtn = new Button();
    // private final Button startNewSessionBtn = new Button();
    // private final Button lastSessionsBtn = new Button();

    public ContextView() {
        
    }

}
