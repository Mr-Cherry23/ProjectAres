import javax.swing.*;
import java.awt.*;

public class Thermometer extends Experiment {
    private double temperature = 20.0;
    private final double MIN_TEMP = -40.0;
    private final double MAX_TEMP = 50.0;

    public Thermometer(long ID) {
        super("thermometer", ID, new boolean[] {true,true,true,true,true});
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;

        int tubeX = centerX - 15;
        int tubeY = 30;
        int tubeWidth = 30;
        int tubeHeight = height - 100;

        int bulbY = tubeY + tubeHeight;
        int bulbRadius = 20;

        // Draw thermometer tube
        g2.setColor(Color.WHITE);
        g2.fillRect(tubeX, tubeY, tubeWidth, tubeHeight);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(tubeX, tubeY, tubeWidth, tubeHeight);

        // Draw bulb
        g2.setColor(Color.WHITE);
        g2.fillOval(centerX - bulbRadius, bulbY - bulbRadius, bulbRadius * 2, bulbRadius * 2);
        g2.setColor(Color.BLACK);
        g2.drawOval(centerX - bulbRadius, bulbY - bulbRadius, bulbRadius * 2, bulbRadius * 2);

        // Calculate mercury level
        double tempRange = MAX_TEMP - MIN_TEMP;
        double tempNormalized = (temperature - MIN_TEMP) / tempRange;
        int mercuryHeight = (int) (tubeHeight * tempNormalized);
        int mercuryY = tubeY + tubeHeight - mercuryHeight;

        // Draw mercury
        g2.setColor(new Color(200, 50, 50));
        g2.fillRect(tubeX + 2, mercuryY, tubeWidth - 4, mercuryHeight);

        // Draw temperature scale
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        for (int i = 0; i <= 10; i++) {
            double temp = MIN_TEMP + (MAX_TEMP - MIN_TEMP) * i / 10;
            int y = tubeY + tubeHeight - (int) (tubeHeight * i / 10.0);
            g2.drawLine(tubeX - 5, y, tubeX - 10, y);
            g2.drawString((int) temp + "°", tubeX - 40, y + 4);
        }

        // Display current temperature
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString(String.format("%.1f°C", temperature), centerX - 30, height - 20);
    }

    public void setTemperature(double temp) {
        this.temperature = Math.max(MIN_TEMP, Math.min(MAX_TEMP, temp));
        repaint();
    }

    public void updateReadings() {
        repaint();
    }
}

    

