import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;


public class Inclinometer extends JPanel {
    double[] attitude = new double[2];

    public Inclinometer() {

        setPreferredSize(new Dimension(120, 120));
    }

    // called by rover/render engine
    public void setAttitude(double pitch, double roll) {
        this.attitude[0] = pitch;
        this.attitude[1] = roll;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int cx = w / 2;
        int cy = h / 2;
        int radius = Math.min(w, h) / 2 - 10;
        Shape oldClip = g2.getClip();

        g2.setClip(new java.awt.geom.Ellipse2D.Double(cx - radius, cy - radius, radius * 2, radius * 2));

        AffineTransform oldTransform = g2.getTransform();

        g2.translate(cx, cy);
        g2.rotate(-attitude[1]);
        int pitchOffset = (int)(attitude[0] * 180);

        g2.setColor(new Color(70, 130, 255));
        g2.fillRect(-radius * 2, -radius * 2 + pitchOffset, radius * 4, radius * 2);

        g2.setColor(new Color(150, 90, 40));
        g2.fillRect(-radius * 2, pitchOffset, radius * 4, radius * 4);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(-radius * 2, pitchOffset, radius * 2, pitchOffset);

        g2.setStroke(new BasicStroke(2));

        for (int deg = -90; deg <= 90; deg += 10) {
            int y = pitchOffset - deg * 4;
            int lineWidth;

            if (deg % 30 == 0) {
                lineWidth = 50;
            } else {
                lineWidth = 25;
            }

            g2.drawLine(-lineWidth / 2, y, lineWidth / 2, y);

            if (deg != 0) {
                g2.drawString( Integer.toString(Math.abs(deg)), lineWidth / 2 + 5, y + 5);
                g2.drawString(Integer.toString(Math.abs(deg)), -lineWidth / 2 - 20, y + 5);
            }
        }

        g2.setTransform(oldTransform);

        g2.setClip(oldClip);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4));
        g2.drawOval(cx - radius, cy - radius, radius * 2, radius * 2);

        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(cx - 40, cy, cx - 10, cy);
        g2.drawLine(cx + 10, cy, cx + 40, cy);
        g2.drawLine(cx - 10, cy, cx + 10, cy);
        g2.drawLine(cx, cy, cx, cy + 10);

        Polygon triangle = new Polygon();

        triangle.addPoint(cx, cy - radius + 5);
        triangle.addPoint(cx - 8, cy - radius + 20);
        triangle.addPoint(cx + 8, cy - radius + 20);
        g2.setColor(Color.WHITE);
        g2.fillPolygon(triangle);
    }

    public void updateReadings() {
        repaint();
    }
}
