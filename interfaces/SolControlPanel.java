import javax.swing.*;
import java.awt.*;

public class SolControlPanel extends JPanel {
    private JLabel solLabel;
    private JButton nextSolButton;
    private Timer updater;

    public SolControlPanel() {
        setPreferredSize(new Dimension(220, 70));
        setLayout(new GridLayout(1, 2, 6, 6));

        solLabel = new JLabel("Sol: -");
        solLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(solLabel);

        nextSolButton = new JButton("Next Sol");
        nextSolButton.addActionListener(e -> {
            if (GameState.powerManager != null) {
                GameState.powerManager.startNewSol();
                refresh();
            } else {
                ConsolePanel.log("[SolControlPanel] No power manager available.");
            }
        });
        add(nextSolButton);

        updater = new Timer(700, e -> refresh());
        updater.start();
    }

    private void refresh() {
        SwingUtilities.invokeLater(() -> {
            int sol = (GameState.powerManager != null) ? GameState.powerManager.getSol() : 0;
            solLabel.setText("Sol: " + sol);
        });
    }
}
