import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HandLenseImager extends Experiment {

    private RenderEngine engine;
    private BufferedImage image;

    private double zoomLevel = 1.0;
    private int offsetX = 0;
    private int offsetY = 0;

    public HandLenseImager(long ID, RenderEngine engine) {
        super("MaHLI (MarsHandLenseImager)", ID,
                new boolean[] {true, true, true, true, true});

        this.engine = engine;

        // gameplay: set power and data characteristics
        this.runPowerCost = 8.0; // power units to run a capture
        this.dataSizeKB = 800; // approximate image size in KB

        setLayout(new BorderLayout());

        try {
            image = ImageIO.read(
                new File("res/experimentImages/mahli/image1.png")
            );
        } catch (Exception error) {
            ConsolePanel.log("[HandLenseImager] " + error.toString());
            return;
        }

        JPanel zoomPanel = new JPanel(new FlowLayout());
        zoomPanel.setPreferredSize(new Dimension(400, 50));

        JButton zoomIn = new JButton("Zoom In");
        zoomIn.addActionListener(event -> zoomIn());

        JButton zoomOut = new JButton("Zoom Out");
        zoomOut.addActionListener(event -> zoomOut());

        JButton capture = new JButton("Capture");
        capture.addActionListener(event -> capture());

        zoomPanel.add(zoomIn);
        zoomPanel.add(zoomOut);
        zoomPanel.add(capture);

        add(zoomPanel, BorderLayout.SOUTH);

        JPanel movePanel = new JPanel(new GridBagLayout());
        GridBagConstraints i = new GridBagConstraints();
        i.fill = GridBagConstraints.BOTH;
        JButton left = new JButton("<=");
        left.addActionListener(event -> moveLeft());
        i.gridx = 0;
        i.gridy = 1;
        movePanel.add(left, i);
        
        JButton upView = new JButton("^");
        upView.addActionListener(event -> moveUp());
        i.gridx = 1;
        i.gridy = 0;
        movePanel.add(upView, i);    

        JButton downView = new JButton("!^");
        downView.addActionListener(event -> moveDown());
        i.gridx = 1;
        i.gridy = 2;
        movePanel.add(downView, i);

        JButton right = new JButton("=>");
        right.addActionListener(event -> moveRight());
        i.gridwidth = GridBagConstraints.REMAINDER;
        i.gridx = 2;
        i.gridy = 1;
        movePanel.add(right, i);

        add(movePanel, BorderLayout.WEST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int scaledWidth = (int)(image.getWidth() * zoomLevel);
        int scaledHeight = (int)(image.getHeight() * zoomLevel);

        // Draw image at custom origin
        g2d.drawImage(image, offsetX, offsetY, scaledWidth, scaledHeight, null);
    }

    private void moveLeft() {
        offsetX += 20;
        repaint();
    }
    
    private void moveRight() {
        offsetX -= 20;
        repaint();
    }
    
    private void moveUp() {
        offsetY += 20;
        repaint();
    }
    
    private void moveDown() {
        offsetY -= 20;
        repaint();
    }

    private void zoomIn() {
        zoomLevel *= 1.1;
        repaint();
    }
    
    private void zoomOut() {
        zoomLevel /= 1.1;
        repaint();
    }

    private void capture() {
        ConsolePanel.log("Capture requested: attempting to run MaHLI");
        runAndTransmit();
    }
}