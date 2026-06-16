import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class CommunicationsPanel extends JPanel{

    public CommunicationsPanel(RenderEngine engine) {
        setPreferredSize(new Dimension(500, 360));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int dishX = -70;
        int dishY = 200;
        int relayX = 100;
        int relayY = 50;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        //Rover Dish------------------------------------------------------------------

        AffineTransform old = g2.getTransform();

        g2.rotate(Math.toRadians(-20));
        
        g2.setColor(new Color(235, 235, 235));
        g2.fillOval(dishX, dishY, 50, 120);

        g2.setColor(Color.BLACK);
        g2.drawOval(dishX, dishY , 50, 120);


        g2.setColor(Color.BLACK);
        g2.drawLine(dishX + 60, dishY + 56, dishX + 15, dishY + 42);
        g2.drawLine(dishX + 60, dishY + 56, dishX + 15, dishY + 70);
        g2.drawLine(dishX + 60, dishY + 56, dishX + 5, dishY + 56);
        
        g2.setColor(new Color(200, 200, 200));
        g2.fillOval(dishX + 55, dishY + 50, 10, 12);
        g2.setColor(Color.BLACK);
        g2.drawOval(dishX + 55, dishY + 50, 10, 12);

        g2.setTransform(old);

        // Satellite Relay------------------------------------------------------------------
        
        g2.rotate(Math.toRadians(10));

        g2.setColor(Color.YELLOW);
        g2.fillRect(relayX + 120, relayY - 15, 50,80);

        Polygon solarPanel = new Polygon ();
        solarPanel.addPoint(relayX, relayY);
        solarPanel.addPoint(relayX + 50, relayY);
        solarPanel.addPoint(relayX + 50, relayY + 20);
        solarPanel.addPoint(relayX + 55, relayY + 20);
        solarPanel.addPoint(relayX + 55, relayY);
        solarPanel.addPoint(relayX + 105, relayY);
        solarPanel.addPoint(relayX + 120, relayY + 25);
        solarPanel.addPoint(relayX + 105, relayY + 50);
        solarPanel.addPoint(relayX + 55, relayY + 50);
        solarPanel.addPoint(relayX + 55, relayY + 30);
        solarPanel.addPoint(relayX + 50, relayY + 30);
        solarPanel.addPoint(relayX + 50 , relayY + 50);
        solarPanel.addPoint(relayX, relayY + 50);

        g2.setColor(new Color(80, 110, 160));
        g2.fillPolygon(solarPanel);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(solarPanel);
        g2.drawRect(relayX + 120, relayY - 15, 50,80);

        
        g2.rotate(Math.toRadians(180), relayX + 150, relayY);
        solarPanel.translate(10, -50);
        g2.setColor(new Color(80, 110, 160));
        g2.fillPolygon(solarPanel);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(solarPanel);

        g2.setTransform(old);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("Communications Relay Network", 120,  270);

    }
    
}
