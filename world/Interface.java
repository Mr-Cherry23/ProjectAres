import javax.swing.*;
import java.awt.*;

public class Interface extends JPanel {

    RenderEngine engine;

    public Interface(RenderEngine engine) {
        this.engine = engine;

        setLayout(null);
        setOpaque(false); // 👈 critical for overlay

        initUI();
    }

    void initUI() {

        JButton resetButton = new JButton("Reset Position");
        resetButton.setBounds(20, 20, 160, 30);

        resetButton.addActionListener(e -> {
            engine.playerPosX = engine.heightMap.getWidth() / 2.0;
            engine.playerPosZ = engine.heightMap.getHeight() / 2.0;

            engine.requestFocusInWindow(); // 🔥 restore control
        });

        add(resetButton);

        JLabel title = new JLabel("Mars Terrain Viewer");
        title.setBounds(20, 60, 200, 30);
        title.setForeground(Color.WHITE);
        add(title);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // HUD-style info
        g.setColor(Color.WHITE);
        g.drawString("X: " + (int)engine.playerPosX, 20, 120);
        g.drawString("Z: " + (int)engine.playerPosZ, 20, 140);
        g.drawString("Angle: " + Math.toDegrees(engine.angle), 20, 160);
    }
}