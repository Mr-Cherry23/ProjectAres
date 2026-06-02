import javax.swing.*;
import java.awt.*;

public class ComunicationsInterface extends JPanel{

    public ComunicationsInterface(RenderEngine engine) {
        setPreferredSize(new Dimension(500, 500));

    }
    
    //Here for safekeeping while i test other RTG designs
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(220, 90, 60, 180);
        g2.setColor(Color.BLACK);
        g2.drawRect(220, 90, 60, 180);

        g2.setColor(new Color(150, 150, 150));
        g2.fillOval(210, 255, 80, 35);
        g2.setColor(Color.BLACK);
        g2.drawOval(210, 255, 80, 35);
        
        g2.setColor(Color.BLUE);
        g2.fillRect(230, 105, 40, 160);

        g2.setColor(new Color(220, 120, 80));
        g2.fillRect(235, 110, 30, 150);
        g2.fillRect(235, 140, 30, 30);
        g2.fillRect(235, 170, 30, 30);





        g2.setColor(new Color(150, 150, 150));
        g2.fillOval(210, 65, 80, 35);
        g2.setColor(Color.BLACK);
        g2.drawOval(210, 65, 80, 35);


    }
}
