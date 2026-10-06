package org.ozis.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.stage.Screen;

public final class Ui {

    private Ui() {}

    public static double scale() {
        // 1. JavaFX знает (Windows, macOS, X11)
        double s = Screen.getPrimary().getOutputScaleX();
        if (s > 1.0) return s;

        // 2. Переменные окружения
        for (String env : new String[]{"GDK_SCALE", "QT_SCALE_FACTOR"}) {
            String v = System.getenv(env);
            if (v != null) {
                try {
                    double parsed = Double.parseDouble(v);
                    if (parsed > 0) return parsed;
                } catch (NumberFormatException ignored) {}
            }
        }

        // 3. Hyprland
        if (System.getenv("HYPRLAND_INSTANCE_SIGNATURE") != null) {
            Double h = detectHyprlandScale();
            if (h != null && h > 0) return h;
        }

        // 4. Fallback
        return 1.0;
    }

    private static Double detectHyprlandScale() {
        try {
            Process p = new ProcessBuilder("hyprctl", "monitors", "-j").start();
            String json;
            try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) sb.append(line);
                json = sb.toString();
            }

            // Берём scale у focused-монитора, иначе — первого
            Matcher m = Pattern.compile(
                "\\{[^}]*\"focused\":\\s*true[^}]*\"scale\":\\s*([0-9.]+)",
                Pattern.DOTALL
            ).matcher(json);
            if (m.find()) return Double.parseDouble(m.group(1));

            m = Pattern.compile("\"scale\":\\s*([0-9.]+)").matcher(json);
            if (m.find()) return Double.parseDouble(m.group(1));
        } catch (Exception ignored) {}
        return null;
    }
}
