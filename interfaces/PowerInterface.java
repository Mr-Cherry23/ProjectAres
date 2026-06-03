import javax.swing.*;
import java.awt.*;


public class PowerInterface extends JPanel{

    public PowerInterface(RenderEngine engine) {
        setPreferredSize(new Dimension(500, 500));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(210, 210, 210));
        g2.fillRect(215, 50, 70, 90);

        Polygon finCleanup = new Polygon();
        finCleanup.addPoint(215, 50);
        finCleanup.addPoint(230, 65);
        finCleanup.addPoint(270, 65);
        finCleanup.addPoint(285, 50);

        g2.setColor(Color.WHITE);
        g2.fillPolygon(finCleanup);

        g2.setColor(new Color(180, 180, 180));
        g2.fillOval(220, 255, 60, 35);
        g2.setColor(Color.BLACK);
        g2.drawOval(220, 255, 60, 35);


        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(220, 80, 60, 190);

        g2.setColor(new Color(180, 180, 180));
        g2.fillOval(220, 60, 60, 35);
        g2.setColor(Color.BLACK);
        g2.drawOval(220, 60, 60, 35);  

        g2.setColor(Color.BLACK);
        g2.drawLine(220, 80, 220, 269);
        g2.drawLine(280, 80, 280, 269);

        g2.setColor(Color.BLACK);
        g2.drawLine(230, 90, 230, 285);
        g2.drawLine(270, 90, 270, 285);
        
        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(230, 90, 40, 180);
        g2.setColor(Color.BLACK);
        g2.drawLine(230, 90, 230, 270);
        g2.drawLine(270, 90, 270, 270);
        g2.drawLine(250, 80, 250, 270);
        g2.drawLine(250, 270, 230, 285);
        g2.drawLine(250, 270, 270, 285);
        g2.drawLine(250, 80, 230, 90);
        g2.drawLine(250, 80, 270, 90);
        
        Polygon pu238 = new Polygon();
        pu238.addPoint(235, 110);
        pu238.addPoint(250, 105);
        pu238.addPoint(265, 110);
        pu238.addPoint(265, 260);
        pu238.addPoint(235, 260);

        Polygon fin = new Polygon();
        fin.addPoint(220, 80);
        fin.addPoint(220, 275);
        fin.addPoint(190, 275);
        fin.addPoint(190, 80);

        Polygon fin2 = new Polygon();
        fin2.addPoint(280, 80);
        fin2.addPoint(280, 275);
        fin2.addPoint(310, 275);
        fin2.addPoint(310, 80);

        g2.setColor(new Color(220, 120, 80));
        g2.fillPolygon(pu238);
        g2.setColor(new Color(230, 230, 230));
        g2.fillPolygon(fin);
        g2.fillPolygon(fin2);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(pu238);
        g2.drawPolygon(fin);
        g2.drawPolygon(fin2);
        g2.drawLine(235, 110, 265, 110);
        g2.drawLine(230, 65, 215, 50);
        g2.drawLine(215, 50, 215, 80);
        g2.drawLine(270, 65, 285, 50);
        g2.drawLine(285, 50, 285, 80);


        


    }

    public void updateReadings() {
        repaint();
    }
    
}
