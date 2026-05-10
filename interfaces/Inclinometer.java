import javax.swing.*;
import java.awt.*;

public class Inclinometer extends JPanel {

    RenderEngine engine;

    public Inclinometer(RenderEngine engine) {

        this.engine = engine;

        setPreferredSize(new Dimension(180, 180));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();

        // center
        int cx = w / 2;
        int cy = h / 2;

        // background circle
        g2.setColor(Color.DARK_GRAY);
        g2.fillOval(0, 0, w, h);

        // clipping circle
        Shape oldClip = g2.getClip();

        g2.setClip(new java.awt.geom.Ellipse2D.Double(
            0, 0, w, h
        ));

        // pitch offset
        int pitchOffset = (int)(engine.pitch * 0.05);

        // rotate by heading
        g2.translate(cx, cy);
        g2.rotate(-engine.angle);

        // sky
        g2.setColor(new Color(100, 140, 255));
        g2.fillRect(-w, -h * 2 + pitchOffset, w * 2, h * 2);

        // ground
        g2.setColor(new Color(140, 90, 60));
        g2.fillRect(-w, pitchOffset, w * 2, h * 2);

        // horizon line
        g2.setColor(Color.WHITE);
        g2.drawLine(-w, pitchOffset, w, pitchOffset);

        // restore transform
        g2.rotate(engine.angle);
        g2.translate(-cx, -cy);

        g2.setClip(oldClip);

        // outer ring
        g2.setColor(Color.WHITE);
        g2.drawOval(0, 0, w - 1, h - 1);

        // center marker
        g2.drawLine(cx - 10, cy, cx + 10, cy);
        g2.drawLine(cx, cy - 10, cx, cy + 10);
    }
}