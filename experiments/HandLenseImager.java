import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HandLenseImager extends Experiment { 
    private RenderEngine engine;
    private JPanel mahli;
    private BufferedImage image;

    public HandLenseImager(long ID, RenderEngine engine) {
        super("MaHLI (MarsHandLenseImager)", ID, new boolean[] {true, true, true, true, true});
        this.engine = engine;
        mahli = new JPanel();
        mahli.setLayout(new BorderLayout());

        try {
            image = ImageIO.read(new File("res/experimentImages/mahli/image1.png"));
        } catch (Exception error) {
            error.printStackTrace();
            return;
        }

        ImageIcon imageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon);
        mahli.add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton zoomInButton = new JButton("Zoom In");
        JButton zoomOutButton = new JButton("Zoom Out");
        JButton captureButton = new JButton("Capture");

        buttonPanel.add(zoomInButton);
        buttonPanel.add(zoomOutButton);
        buttonPanel.add(captureButton);
    
        mahli.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JPanel getMahli() {
        return mahli;
    }
    

    
    
}
