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






        
        g2.setColor(new Color(180, 180, 180));
        g2.fillOval(220, 255, 60, 35);
        g2.setColor(Color.BLACK);
        g2.drawOval(220, 255, 60, 35);

        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(220, 90, 60, 180);
        
        g2.setColor(Color.BLACK);
        g2.drawLine(220, 90, 220, 269);
        g2.drawLine(280, 90, 280, 269);

        g2.setColor(Color.BLACK);
        g2.drawLine(230, 90, 230, 285);
        g2.drawLine(270, 90, 270, 285);
        
        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(230, 90, 40, 180);
        g2.setColor(Color.BLACK);
        g2.drawLine(230, 90, 230, 270);
        g2.drawLine(270, 90, 270, 270);
        g2.drawLine(250, 90, 250, 270);
        g2.drawLine(250, 270, 230, 285);
        g2.drawLine(250, 270, 270, 285);


        


    }

    public void updateReadings() {
        repaint();
    }
    
}
