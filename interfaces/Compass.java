import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Compass extends JPanel {

    double heading = 0;
    double cameraAngle;

    public Compass() {

        setPreferredSize(new Dimension(120, 120));
    }

    // called by rover/render engine
    public void setHeading(double heading) {
        this.heading = heading;
    }

    public void setCameraAngle(double cameraAngle) {
        this.cameraAngle = cameraAngle;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int radius = Math.min(centerX, centerY) - 10;

        // background
        g2.setColor(new Color(40, 40, 40));
        g2.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        // inner face
        g2.setColor(new Color(220, 220, 220));
        g2.fillOval(centerX - radius + 4, centerY - radius + 4, radius * 2 - 8, radius * 2 - 8);

        // save transform
        AffineTransform old = g2.getTransform();

        // rotate compass ring
        g2.translate(centerX, centerY);
        g2.rotate(-heading);

        // draw gradations
        for (int deg = 0; deg < 360; deg += 10) {

            double rad = Math.toRadians(deg);

            int outerX = (int)(Math.sin(rad) * (radius - 8));
            int outerY = (int)(-Math.cos(rad) * (radius - 8));

            int innerLength;

            // larger ticks every 30°
            if (deg % 30 == 0) {
                innerLength = 18;
            } else {
                innerLength = 10;
            }

            int innerX = (int)(Math.sin(rad) * (radius - 8 - innerLength));
            int innerY = (int)(-Math.cos(rad) * (radius - 8 - innerLength));

            g2.setColor(Color.BLACK);

            g2.drawLine(innerX, innerY, outerX, outerY);

            // labels every 90°
            if (deg % 90 == 0) {

                String label = "";

                switch (deg) {
                    case 0: label = "N"; break;
                    case 90: label = "E"; break;
                    case 180: label = "S"; break;
                    case 270: label = "W"; break;
                }

                int tx = (int)(Math.sin(rad) * (radius - 30));
                int ty = (int)(-Math.cos(rad) * (radius - 30));

                g2.drawString(label, tx - 4, ty + 5);
            }
        }
        // restore transform
        g2.setTransform(old);
        g2.setColor(Color.RED);

        Polygon marker = new Polygon();

        marker.addPoint(centerX, centerY - 14);
        marker.addPoint(centerX - 4, centerY - 7);
        marker.addPoint(centerX - 4, centerY + 7);
        marker.addPoint(centerX + 4, centerY + 7);
        marker.addPoint(centerX + 4, centerY - 7);

        g2.fillPolygon(marker);

        AffineTransform old2 = g2.getTransform();

        g2.translate(centerX, centerY);
        g2.rotate(cameraAngle);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine( 0, 10, 0, -radius + 25);

        g2.setTransform(old2);

        g2.setColor(Color.GRAY);

        g2.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    public void updateReadings() {
        repaint();
    }
}