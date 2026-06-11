import javax.swing.*;
import java.awt.*;

public class MissionPanel extends JPanel {
    private JLabel solLabel;
    private JLabel missionLabel;
    private JLabel remainingLabel;
    private JLabel statusLabel;
    private JLabel coordLabel;

    public MissionPanel() {
        setPreferredSize(new Dimension(300, 180));
        setLayout(new GridLayout(5,1));
        solLabel = new JLabel("Sol: -");
        missionLabel = new JLabel("Mission: none");
        remainingLabel = new JLabel("Remaining: -");
        statusLabel = new JLabel("Status: -");
        coordLabel = new JLabel("Pos: - , -");
        add(solLabel);
        add(missionLabel);
        add(remainingLabel);
        add(statusLabel);
        add(coordLabel);

        Timer t = new Timer(700, e -> refresh());
        t.start();
    }

    private void refresh() {
        SwingUtilities.invokeLater(() -> {
            int sol = (GameState.powerManager != null) ? GameState.powerManager.getSol() : 0;
            solLabel.setText("Sol: " + sol);
            Mission m = (GameState.missionManager != null) ? GameState.missionManager.getCurrent() : null;
            double px = (GameState.engine != null) ? GameState.engine.getPlayerPosX() : 0.0;
            double pz = (GameState.engine != null) ? GameState.engine.getPlayerPosZ() : 0.0;
            coordLabel.setText(String.format("Pos: %d, %d", (int)px, (int)pz));

            if (m == null) {
                missionLabel.setText("Mission: none");
                remainingLabel.setText("Remaining: -");
                statusLabel.setText("Status: idle");
            } else {
                missionLabel.setText(String.format("Mission: go to (%d,%d) and run '%s'", (int)m.targetX, (int)m.targetZ, m.requiredExperiment));
                remainingLabel.setText("Sols remaining: " + m.solsRemaining(sol));
                String s = m.completed ? "COMPLETED" : m.failed ? "FAILED" : "ACTIVE";
                statusLabel.setText("Status: " + s);
            }
        });
    }
}
