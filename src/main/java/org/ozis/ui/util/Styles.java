package org.ozis.ui.util;

import javafx.scene.Scene;

public final class Styles {

    private static final String[] SHEETS = {
        "/styles/base.css",
        "/styles/controls.css",
        "/styles/panels.css",
        "/styles/views.css",
        "/styles/windows.css",
        "/styles/dialogs.css",
    };

    private Styles() {}

    public static void apply(Scene scene) {
        for (String path : SHEETS) {
            var url = Styles.class.getResource(path);
            if (url == null) {
                System.err.println("CSS not found: " + path);
                continue;
            }
            scene.getStylesheets().add(url.toExternalForm());
        }
    }

    // для диалогов (Notifier)
    public static String[] sheets() {
        return SHEETS;
    }
}
