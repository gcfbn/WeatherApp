package app.utils;
import app.utils.Callable;

import javax.swing.*;

public class AppUtils {

    public static void invokeLater(Callable callable) {
        SwingUtilities.invokeLater(() -> {
            try {
                callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
