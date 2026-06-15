import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DraggablePanel extends JPanel {

    private Point initialClick;

    public DraggablePanel(JPanel content) {

        setLayout(new BorderLayout());

        // Optional styling
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.LIGHT_GRAY);

        // Add the provided panel
        setSize(content.getPreferredSize());
        add(content, BorderLayout.CENTER);

        // Mouse listener for dragging
        MouseAdapter ma = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                // Current location
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Mouse movement
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // New location
                int newX = thisX + xMoved;
                int newY = thisY + yMoved;

                setLocation(newX, newY);
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
    }
}