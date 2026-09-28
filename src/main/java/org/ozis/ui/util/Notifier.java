package org.ozis.ui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Window;

public final class Notifier {

    private Notifier() {}

    // ==== Информация ====

    public static void info(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("External-Memory - " + title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        style(alert);
        alert.showAndWait();
    }

    // ==== Ошибка ====

    public static void error(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("External-Memory - " + title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        style(alert);
        alert.showAndWait();
    }

    // ==== Предупреждение ====

    public static void warn(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("External-Memory - " + title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        style(alert);
        alert.showAndWait();
    }

    // ==== Подтверждение ====

    public static boolean confirm(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("External-Memory - " + title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        style(alert);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    // ==== Ввод текста ====

    public static Optional<String> askText(String title, String prompt) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("External-Memory - " + title);
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        style(dialog);
        return dialog.showAndWait();
    }

    // ==== Общая стилизация ====

    private static void style(javafx.scene.control.Dialog<?> dialog) {
        Window owner = getActiveWindow();
        if (owner != null) {
            dialog.initOwner(owner);
        }
        dialog.getDialogPane().getStylesheets().add(
            Notifier.class.getResource("/styles/main.css").toExternalForm()
        );
        dialog.getDialogPane().getStyleClass().add("em-dialog");
    }

    private static Window getActiveWindow() {
        return javafx.stage.Window.getWindows().stream()
            .filter(Window::isFocused)
            .findFirst()
            .orElse(null);
    }
}
