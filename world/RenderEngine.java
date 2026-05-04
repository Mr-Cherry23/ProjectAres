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
    double pitch = 0;

    double minHeight = 255;
    double maxHeight = 0;

    public RenderEngine() {

        try {
            heightMap = ImageIO.read(new File("res/map1/heightmap1.png"));
            textureMap = ImageIO.read(new File("res/map1/texturemap1.png"));

            computeMinMax();

            playerPosX = heightMap.getWidth() / 2.0;
            playerPosZ = heightMap.getHeight() / 2.0;

        } catch (Exception error) {
            error.printStackTrace();
            System.exit(1);
        }

        // loop
        Timer timer = new Timer(16, e -> {
            repaint();
        });
        timer.start();
    }

    // 🎮 Movement methods (CALLED FROM UI)

    public void rotateLeft() {
        angle -= Math.toRadians(15);
    }

    public void rotateRight() {
        angle += Math.toRadians(15);
    }

    public void lookUp() {
        pitch += 150;
        pitch = Math.max(-2000, Math.min(2000, pitch));
    }
    
    public void lookDown() {
        pitch -= 150;
        pitch = Math.max(-2000, Math.min(2000, pitch));
    }

    public void moveForward() {
        double speed = 5;
        playerPosX += Math.cos(angle) * speed;
        playerPosZ += Math.sin(angle) * speed;
    }

    public void moveBackward() {
        double speed = 5;
        playerPosX -= Math.cos(angle) * speed;
        playerPosZ -= Math.sin(angle) * speed;
    }

    public void resetPosition() {
        playerPosX = heightMap.getWidth() / 2.0;
        playerPosZ = heightMap.getHeight() / 2.0;
    }

    // --- HEIGHT + TEXTURE (unchanged) ---

    double getHeight(double x, double z) {
        int x0 = (int)Math.floor(x);
        int z0 = (int)Math.floor(z);
        int x1 = x0 + 1;
        int z1 = z0 + 1;

        double h00 = getPixelHeight(x0, z0);
        double h10 = getPixelHeight(x1, z0);
        double h01 = getPixelHeight(x0, z1);
        double h11 = getPixelHeight(x1, z1);

        double tx = x - x0;
        double tz = z - z0;

        double hx0 = h00 * (1 - tx) + h10 * tx;
        double hx1 = h01 * (1 - tx) + h11 * tx;

        return hx0 * (1 - tz) + hx1 * tz;
    }

    double getPixelHeight(int x, int z) {
        if (x < 0 || z < 0 || x >= heightMap.getWidth() || z >= heightMap.getHeight())
            return 0;

        int raw = heightMap.getRGB(x, z) & 0xFF;
        double h = (raw - minHeight) / (maxHeight - minHeight + 0.0001);
        h = Math.pow(h, 0.9);
        return h * 20;
    }

    Color sampleTexture(double x, double z) {

        double tx = x / heightMap.getWidth() * textureMap.getWidth();
        double tz = z / heightMap.getHeight() * textureMap.getHeight();

        int ix = (int)tx;
        int iz = (int)tz;

        ix = Math.max(0, Math.min(textureMap.getWidth() - 1, ix));
        iz = Math.max(0, Math.min(textureMap.getHeight() - 1, iz));

        return new Color(textureMap.getRGB(ix, iz));
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

        g.setColor(new Color(210, 140, 100));
        g.fillRect(0, 0, w, h / 2);

        double eye = getHeight(playerPosX, playerPosZ) + 1;

        for (int x = 0; x < w; x++) {

            double rayAngle = (angle - 0.35) + (x / (double) w) * 0.7;
            double maxY = h;

            for (int d = 1; d < 500; d++) {

                double rx = playerPosX + Math.cos(rayAngle) * d;
                double rz = playerPosZ + Math.sin(rayAngle) * d;

                double hgt = getHeight(rx, rz);

                double proj = (hgt - eye) / (d * 0.6) * 1500;
                int sy = (int)(h / 2 - proj + pitch);

                if (sy < maxY) {

                    Color base = sampleTexture(rx, rz);

                    double fog = Math.pow(d / 500.0, 2);

                    int r = (int)(base.getRed() * (1 - fog) + 210 * fog);
                    int gCol = (int)(base.getGreen() * (1 - fog) + 140 * fog);
                    int b = (int)(base.getBlue() * (1 - fog) + 100 * fog);

                    g.setColor(new Color(r, gCol, b));
                    g.drawLine(x, sy, x, (int)maxY);

                    maxY = sy;
                }
            }
        }
    }
}