import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HandLenseImager extends Experiment { 
    private JPanel panel;
    private BufferedImage image;

    public HandLenseImager(long ID) {
        super("MaHLI (MarsHandLenseImager)", ID, new boolean[] {true, true, true, true, true});

        // Create the panel to hold the UI components
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        try {
            image = ImageIO.read(new File("res/experimentImages/mahli/image1.png"));
        } catch (Exception error) {
            error.printStackTrace();
            return;
        }

        // Add the image to the panel
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, BorderLayout.CENTER);

        // Create a button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Add buttons to the button panel
        JButton zoomInButton = new JButton("Zoom In");
        JButton zoomOutButton = new JButton("Zoom Out");
        JButton captureButton = new JButton("Capture");

        buttonPanel.add(zoomInButton);
        buttonPanel.add(zoomOutButton);
        buttonPanel.add(captureButton);

        // Add the button panel to the main panel
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }
    

    
    
}
