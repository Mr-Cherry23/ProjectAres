import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class RenderEngine extends JPanel {

    BufferedImage heightMap;
    BufferedImage textureMap;

    double playerPosX;
    double playerPosZ;
    double angle = 0;

    double minHeight = 255;
    double maxHeight = 0;

    KeyDetect controller;

    public RenderEngine() {

        controller = new KeyDetect();

        try {
            heightMap = ImageIO.read(new File("res/map1/heightmap1.png"));
            textureMap = ImageIO.read(new File("res/map1/texturemap1.png"));

            if (heightMap == null) {
                throw new RuntimeException("Failed to load height map.");            
            }
            if (textureMap == null) {
                throw new RuntimeException("Failed to load texture map.");
            }

            computeMinMax();

            playerPosX = heightMap.getWidth() / 2.0;
            playerPosZ = heightMap.getHeight() / 2.0;

        } catch (Exception error) {
            error.printStackTrace();
            System.exit(1);
        }

        setFocusable(true);
        addKeyListener(controller);

        //loop
        Timer timer = new Timer(1, event -> {
            update();
            repaint();
        });
        timer.start();
    }

    void update() {

        double rotateStep = Math.toRadians(15);

        if (controller.left) {
            angle -= rotateStep;
        }
        
        if (controller.right) {
            angle += rotateStep;
        }


    }


    double getHeight(double locationX, double locationZ) {

        int x0 = (int)Math.floor(locationX);
        int z0 = (int)Math.floor(locationZ);
        int x1 = x0 + 1;
        int z1 = z0 + 1;

        double h00 = getPixelHeight(x0, z0);
        double h10 = getPixelHeight(x1, z0);
        double h01 = getPixelHeight(x0, z1);
        double h11 = getPixelHeight(x1, z1);

        double tx = locationX - x0;
        double tz = locationZ - z0;

        double hx0 = h00 * (1 - tx) + h10 * tx;
        double hx1 = h01 * (1 - tx) + h11 * tx;

        return hx0 * (1 - tz) + hx1 * tz;
    }

    double getPixelHeight(int locationX, int locationZ) {
        // "height" here refferes to the raw grayscale colour value of the pixel.

        if (locationX < 0 || locationZ < 0 || locationX >= heightMap.getWidth() || locationZ >= heightMap.getHeight()) {
            return 0;
        }
        
        int rawHeight = heightMap.getRGB(locationX, locationZ) & 0xFF;

        double correctedHeight = (rawHeight - minHeight) / (maxHeight - minHeight + 0.0001);

        correctedHeight = Math.pow(correctedHeight, 0.9);

        return correctedHeight * 20;
    }

    // 🎨 Texture sampling (bilinear)
    Color sampleTexture(double x, double z) {

        double tx = x / heightMap.getWidth() * textureMap.getWidth();
        double tz = z / heightMap.getHeight() * textureMap.getHeight();

        int x0 = (int)Math.floor(tx);
        int z0 = (int)Math.floor(tz);
        int x1 = x0 + 1;
        int z1 = z0 + 1;

        double fx = tx - x0;
        double fz = tz - z0;

        Color c00 = getTexel(x0, z0);
        Color c10 = getTexel(x1, z0);
        Color c01 = getTexel(x0, z1);
        Color c11 = getTexel(x1, z1);

        int r = (int)(
            (c00.getRed() * (1 - fx) + c10.getRed() * fx) * (1 - fz) +
            (c01.getRed() * (1 - fx) + c11.getRed() * fx) * fz
        );

        int g = (int)(
            (c00.getGreen() * (1 - fx) + c10.getGreen() * fx) * (1 - fz) +
            (c01.getGreen() * (1 - fx) + c11.getGreen() * fx) * fz
        );

        int b = (int)(
            (c00.getBlue() * (1 - fx) + c10.getBlue() * fx) * (1 - fz) +
            (c01.getBlue() * (1 - fx) + c11.getBlue() * fx) * fz
        );

        return new Color(ensureInRange(r), ensureInRange(g), ensureInRange(b));
    }

    Color getTexel(int x, int z) {
        x = Math.max(0, Math.min(textureMap.getWidth() - 1, x));
        z = Math.max(0, Math.min(textureMap.getHeight() - 1, z));
        return new Color(textureMap.getRGB(x, z));
    }

    int ensureInRange(int value) {
        return Math.max(0, Math.min(255, value));
    }

    void computeMinMax() {
        for (int y = 0; y < heightMap.getHeight(); y++) {
            for (int x = 0; x < heightMap.getWidth(); x++) {
                int p = heightMap.getRGB(x, y) & 0xFF;
                if (p < minHeight) minHeight = p;
                if (p > maxHeight) maxHeight = p;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        //set sky colour as background colour
        g.setColor(new Color(210, 140, 100));
        g.fillRect(0, 0, w, h / 2);

        double eyeLevel = getHeight(playerPosX, playerPosZ) + 10;

        for (int x = 0; x < w; x++) {

            double rayAngle = (angle - 0.35) + (x / (double) w) * 0.7;
            double maxY = h;

            for (int depth = 1; depth < 500; depth++) {

                double rx = playerPosX + Math.cos(rayAngle) * depth;
                double rz = playerPosZ + Math.sin(rayAngle) * depth;

                double height = getHeight(rx, rz);

                double proj = (height - eyeLevel) / (depth * 0.6) * 1500;
                int screenY = (int)(h / 2 - proj);

                if (screenY < maxY) {

                    Color base = sampleTexture(rx, rz);

                    double dx = getHeight(rx + 1, rz) - getHeight(rx - 1, rz);
                    double dz = getHeight(rx, rz + 1) - getHeight(rx, rz - 1);

                    double light = 1 - (dx + dz) * 0.05;
                    light = Math.max(0.3, Math.min(1.2, light));

                    double fog = Math.pow(depth / 500.0, 2);

                    int r = (int)(base.getRed() * light * (1 - fog) + 210 * fog);
                    int gCol = (int)(base.getGreen() * light * (1 - fog) + 140 * fog);
                    int b = (int)(base.getBlue() * light * (1 - fog) + 100 * fog);

                    g.setColor(new Color(ensureInRange(r), ensureInRange(gCol), ensureInRange(b)));
                    g.drawLine(x, screenY, x, (int)maxY);

                    maxY = screenY;
                }
            }
        }
    }
}