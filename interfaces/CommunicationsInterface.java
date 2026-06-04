import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class CommunicationsInterface extends JPanel{

    public CommunicationsInterface(RenderEngine engine) {
        setPreferredSize(new Dimension(200, 360));

    }
    
    //Here for safekeeping while i test other RTG designs
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 0; // top-left x of the image
        int y = 50; // top-left y of the image
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        AffineTransform old = g2.getTransform();

        g2.rotate(Math.toRadians(-20));
        
        g2.setColor(new Color(235, 235, 235));
        g2.fillOval(x, y, 50, 120);

        g2.setColor(Color.BLACK);
        g2.drawOval(x, y , 50, 120);

        g2.setColor(new Color(200, 200, 200));
        g2.fillOval(x + 55, y + 50, 10, 12);

        g2.setColor(Color.BLACK);
        g2.drawOval(x + 55, y + 50, 10, 12);

        g2.drawLine(x + 60, y + 56, x + 15, y + 42);
        g2.drawLine(x + 60, y + 56, x + 15, y + 70);
        g2.drawLine(x + 60, y + 56, x + 5, y + 56);

        g2.setTransform(old);
    }
    
}
