package org.ozis.ui;

import javafx.scene.Scene;

public final class Styles {
    private static final String MAIN = "/styles/main.css";

    private Styles() {}

    public static void apply(Scene scene) {
        var url = Styles.class.getResource(MAIN);
        System.out.println("CSS URL: " + url);
        if (url == null) {
            System.err.println("CSS not found: " + MAIN);
            return;
        }
        scene.getStylesheets().add(url.toExternalForm());
    }
}
