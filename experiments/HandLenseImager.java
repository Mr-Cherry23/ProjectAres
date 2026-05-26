import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HandLenseImager extends Experiment { 
    private RenderEngine engine;
    private BufferedImage image;

    public HandLenseImager(long ID, RenderEngine engine) {
        super("MaHLI (MarsHandLenseImager)", ID, new boolean[] {true, true, true, true, true});
        this.engine = engine;
        
        setLayout(new BorderLayout());

        try {
            image = ImageIO.read(new File("res/experimentImages/mahli/image1.png"));
        } catch (Exception error) {
            error.printStackTrace();
            return;
        }

        ImageIcon imageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon);
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setPreferredSize(new Dimension(400, 50));
        JButton zoomIn = new JButton("Zoom In");
        zoomIn.addActionListener(event -> zoomIn());
        JButton zoomOut = new JButton("Zoom Out");
        zoomIn.addActionListener(event -> zoomOut());
        JButton capture = new JButton("Capture");
        zoomIn.addActionListener(event -> capture());

        buttonPanel.add(zoomIn);
        buttonPanel.add(zoomOut);
        buttonPanel.add(capture);
    
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void zoomIn() {

    }

    private void zoomOut() {

    }

    private void capture() {

    }


}
