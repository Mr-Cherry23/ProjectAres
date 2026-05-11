import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Compass extends JPanel {

    double heading = 0;


    public Compass() {

        setPreferredSize(new Dimension(120, 120));

    }

    // called by rover/render engine
    public void setHeading(double heading) {
        this.heading = heading;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(centerX, centerY) - 10;

        // Draw compass circle
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        g2.setColor(Color.BLACK);
        g2.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

        // Draw needle
        AffineTransform old = g2.getTransform();
        g2.translate(centerX, centerY);
        g2.rotate(heading);
        
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(0, 0, 0, -radius + 20); // North needle
        g2.setColor(Color.BLACK);
        g2.drawLine(0, 0, 0, radius - 20); // South needle
        
        g2.setTransform(old);
    }

    public void updateReadings() {
        repaint();
    }
}