import javax.swing.*;

public class ConsolePanel {
    private static JTextArea textArea;

    public static void setTextArea(JTextArea ta) {
        textArea = ta;
    }

    public static void log(String s) {
        if (textArea != null) {
            final String msg = s;
            SwingUtilities.invokeLater(() -> {
                textArea.append(msg + "\n");
                textArea.setCaretPosition(textArea.getDocument().getLength());
            });
        }
    }

    public static void clear() {
        if (textArea != null) SwingUtilities.invokeLater(() -> textArea.setText(""));
    }
}
