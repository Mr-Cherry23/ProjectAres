import javax.swing.*;
import java.awt.*;


public class RTGPanel extends JPanel{

    public RTGPanel(RenderEngine engine) {
        setPreferredSize(new Dimension(500, 500));
        repaint();
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 0; // top-left x of the image
        int y = 0; // top-left y of the image
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(210, 210, 210));
        g2.fillRect(x + 25, y + 0, 70, 90);
        
        Polygon finCleanup = new Polygon();
        finCleanup.addPoint(x + 25, y + 0);
        finCleanup.addPoint(x + 40, y + 15);
        finCleanup.addPoint(x + 80, y + 15);
        finCleanup.addPoint(x + 95, y + 0);
        
        g2.setColor(Color.WHITE);
        g2.fillPolygon(finCleanup);
        
        g2.setColor(new Color(180, 180, 180));
        g2.fillOval(x + 30, y + 205, 60, 35);
        g2.setColor(Color.BLACK);
        g2.drawOval(x + 30, y + 205, 60, 35);
        
        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(x + 30, y + 30, 60, 190);
        
        g2.setColor(new Color(180, 180, 180));
        g2.fillOval(x + 30, y + 10, 60, 35);
        g2.setColor(Color.BLACK);
        g2.drawOval(x + 30, y + 10, 60, 35);
        
        g2.setColor(Color.BLACK);
        g2.drawLine(x + 30, y + 30, x + 30, y + 219);
        g2.drawLine(x + 90, y + 30, x + 90, y + 219);
        
        g2.setColor(Color.BLACK);
        g2.drawLine(x + 40, y + 40, x + 40, y + 235);
        g2.drawLine(x + 80, y + 40, x + 80, y + 235);
        
        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(x + 40, y + 40, 40, 180);
        g2.setColor(Color.BLACK);
        g2.drawLine(x + 40, y + 40, x + 40, y + 220);
        g2.drawLine(x + 80, y + 40, x + 80, y + 220);
        g2.drawLine(x + 60, y + 30, x + 60, y + 220);
        g2.drawLine(x + 60, y + 220, x + 40, y + 235);
        g2.drawLine(x + 60, y + 220, x + 80, y + 235);
        g2.drawLine(x + 60, y + 30, x + 40, y + 40);
        g2.drawLine(x + 60, y + 30, x + 80, y + 40);
        
        Polygon pu238 = new Polygon();
        pu238.addPoint(x + 45, y + 60);
        pu238.addPoint(x + 60, y + 55);
        pu238.addPoint(x + 75, y + 60);
        pu238.addPoint(x + 75, y + 210);
        pu238.addPoint(x + 45, y + 210);
        
        Polygon fin = new Polygon();
        fin.addPoint(x + 30, y + 30);
        fin.addPoint(x + 30, y + 225);
        fin.addPoint(x + 0, y + 225);
        fin.addPoint(x + 0, y + 30);
        
        Polygon fin2 = new Polygon();
        fin2.addPoint(x + 90, y + 30);
        fin2.addPoint(x + 90, y + 225);
        fin2.addPoint(x + 120, y + 225);
        fin2.addPoint(x + 120, y + 30);
        
        float health = 1.0f;
        if (GameState.powerManager != null) health = (float) GameState.powerManager.getRTGHealth();
        Color start = new Color(255, 117, 24); // pumpkin orange
        Color end = new Color(40, 10, 10); // dark mahogany/blackish
        int r = (int) (start.getRed() * health + end.getRed() * (1 - health));
        int gg = (int) (start.getGreen() * health + end.getGreen() * (1 - health));
        int b = (int) (start.getBlue() * health + end.getBlue() * (1 - health));
        Color core = new Color(Math.max(0, Math.min(255, r)), Math.max(0, Math.min(255, gg)), Math.max(0, Math.min(255, b)));
        g2.setColor(core);
        g2.fillPolygon(pu238);
        g2.setColor(new Color(230, 230, 230));
        g2.fillPolygon(fin);
        g2.fillPolygon(fin2);
        
        g2.setColor(Color.BLACK);
        g2.drawPolygon(pu238);
        g2.drawPolygon(fin);
        g2.drawPolygon(fin2);
        
        g2.drawLine(x + 45, y + 60, x + 75, y + 60);
        g2.drawLine(x + 40, y + 15, x + 25, y + 0);
        g2.drawLine(x + 25, y + 0, x + 25, y + 30);
        g2.drawLine(x + 80, y + 15, x + 95, y + 0);
        g2.drawLine(x + 95, y + 0, x + 95, y + 30);


        


    }

    public void updateReadings() {
        repaint();
    }
    
}
