import javax.swing.*;
import java.awt.*;

public class ConsolePanel extends JPanel{
    private static JTextArea textArea;

    public ConsolePanel() {
        setPreferredSize(new Dimension(620, 500));
        setLayout(new BorderLayout());
        
        initUI();
    }

    private void initUI() {        
        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField consoleInput = new JTextField();
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton runNow = new JButton("Run Now");
        JButton schedule = new JButton("Schedule Next Sol");
        
        
        JTextArea consoleArea = new JTextArea();
        consoleArea.setEditable(false);
        setTextArea(consoleArea);
        JScrollPane consoleScroll = new JScrollPane(consoleArea);
        add(consoleScroll, BorderLayout.CENTER);
        
        buttonRow.add(schedule);
        buttonRow.add(runNow);

        inputPanel.add(consoleInput, BorderLayout.CENTER);
        inputPanel.add(buttonRow, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
        

        // wire input actions
        runNow.addActionListener(e -> {
            String text = consoleInput.getText();
            if (text != null && !text.trim().isEmpty() && GameState.commandScheduler != null) {
                GameState.commandScheduler.executeFromText(text);
                consoleInput.setText("");
            }
        });
        schedule.addActionListener(e -> {
            String text = consoleInput.getText();
            if (text != null && !text.trim().isEmpty() && GameState.commandScheduler != null) {
                GameState.commandScheduler.scheduleFromText(text);
                consoleInput.setText("");
            }
        });
        consoleInput.addActionListener(e -> runNow.doClick());
    }
    public static void setTextArea(JTextArea ta) {
        textArea = ta;
    }
    
    public static void log(String s) {
        if (textArea != null) {
            final String msg = s;
            SwingUtilities.invokeLater(() -> {
                textArea.append(msg + "\n\n");
                textArea.setCaretPosition(textArea.getDocument().getLength());
            });
        }
    }

    public static void clear() {
        if (textArea != null) SwingUtilities.invokeLater(() -> textArea.setText(""));
    }
}
