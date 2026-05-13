import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class RenderEngine extends JPanel {

    ScienceInterface scienceInterface;
    SensorInterface sensors;

    BufferedImage heightMap;
    BufferedImage textureMap;
    BufferedImage rockTexture;

    ArrayList<Feature> features = new ArrayList<>();
    double[] roverAttitude = new double[3];
    double playerPosX;
    double playerPosZ;
    double roverWidth = 12;
    double roverLength = 18;
    double cameraAngle = 0;
    double pitch = 0;

    double minHeight = 65535;
    double maxHeight = 0;

    public RenderEngine() {

        try {
            heightMap = ImageIO.read(new File("res/map2/heightmap2.png"));
            textureMap = ImageIO.read(new File("res/map2/texturemap2.png"));
            rockTexture = ImageIO.read(new File("res/other/rock.png"));
            
            playerPosX = heightMap.getWidth() / 2.0;
            playerPosZ = heightMap.getHeight() / 2.0;
    
        } catch (Exception error) {
            error.printStackTrace();
            System.exit(1);
        }
        
        setPreferredSize(new Dimension(1280, 720));

        features.add(new Rock(730, 1670, 2));
        features.add(new Rock(850, 1670, 3));
        features.add(new Rock(800, 500, 5));

        Timer timer = new Timer(16, e -> {
            repaint();
            updateAttitude();
        });
        
        timer.start();
    }

    void setInterfaces(ScienceInterface scienceInterface, SensorInterface sensors) {
        this.scienceInterface = scienceInterface;
        this.sensors = sensors;
    }
    
    public void rotateLeft() {
        cameraAngle -= Math.toRadians(15);
        sensors.updateReadings();
    }

    public void rotateRight() {
        cameraAngle += Math.toRadians(15);
        sensors.updateReadings();
    }

    public void turnLeft() {
        roverAttitude[0] -= Math.toRadians(15);
        cameraAngle -= Math.toRadians(15);
        sensors.updateReadings();
    }

    public void turnRight() {
        roverAttitude[0] += Math.toRadians(15);
        cameraAngle += Math.toRadians(15);
        sensors.updateReadings();
    }

    public void lookUp() {
        pitch += 150;
        pitch = Math.max(-20000, Math.min(20000, pitch));
        sensors.updateReadings();
    }
    
    public void lookDown() {
        pitch -= 150;
        pitch = Math.max(-20000, Math.min(20000, pitch));
        sensors.updateReadings();
    }

    public void moveForward() {
        double speed = 5;
        playerPosX += Math.cos(roverAttitude[0]) * speed;
        playerPosZ += Math.sin(roverAttitude[0]) * speed;
        scienceInterface.updateReadings();
        sensors.updateReadings();
    }

    public void moveBackward() {
        double speed = 5;
        playerPosX -= Math.cos(roverAttitude[0]) * speed;
        playerPosZ -= Math.sin(roverAttitude[0]) * speed;
        scienceInterface.updateReadings();
        sensors.updateReadings();
    }

    public void resetPosition() {
        playerPosX = heightMap.getWidth() / 2.0;
        playerPosZ = heightMap.getHeight() / 2.0;
        scienceInterface.updateReadings();
        sensors.updateReadings();
    }

    public double getHeight(double x, double z) {
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

        double terrainHeight = hx0 * (1 - tz) + hx1 * tz;
    
        for (Feature feature : features) {
    
            if (feature instanceof Rock) {
                Rock rock = (Rock) feature;

                double dx = x - rock.x;
                double dz = z - rock.z;

                double dist = Math.sqrt(dx * dx + dz * dz);

                if (dist < rock.radius) {
                    double sphereHeight = Math.sqrt(rock.radius * rock.radius- dist * dist);
                    sphereHeight *= 0.7;
                    sphereHeight += Math.sin(x * 1.005) * Math.cos(z * 1.005) * 0.50;
                    terrainHeight += sphereHeight;
                }
            }
        }
        return terrainHeight;
    }

    public double getPixelHeight(int x, int z) {

        if (x < 0 || z < 0 || x >= heightMap.getWidth() || z >= heightMap.getHeight()) {
            return 0;
        }

        int raw = heightMap.getRaster().getSample(x, z, 0);    
        double normalized = raw / 65535.0;  
        
        normalized = Math.pow(normalized, 0.9);
    
        return normalized * 10000;
    }

    public Color sampleTexture(double x, double z) {

        double tx = x / heightMap.getWidth() * textureMap.getWidth();
        double tz = z / heightMap.getHeight() * textureMap.getHeight();

        int ix = (int)tx;
        int iz = (int)tz;

        ix = Math.max(0, Math.min(textureMap.getWidth() - 1, ix));
        iz = Math.max(0, Math.min(textureMap.getHeight() - 1, iz));

        Color terrainColor = new Color(textureMap.getRGB(ix, iz));

        for (Feature f : features) {

            if (f instanceof Rock) {

                Rock rock = (Rock) f;

                double dx = x - rock.x;
                double dz = z - rock.z;

                double dist = Math.sqrt(dx * dx + dz * dz);

                if (dist < rock.radius) {
                    
                    double influence = 1.0 - (dist / rock.radius);
                    influence = Math.pow(influence, 0.1);
                    
                    int rx = (int)(x * 4) % rockTexture.getWidth();
                    int rz = (int)(z * 4) % rockTexture.getHeight();

                    if (rx < 0) {
                        rx += rockTexture.getWidth();
                    }
                    if (rz < 0) {
                        rz += rockTexture.getHeight();
                    }
                    
                    Color rockColor = new Color(rockTexture.getRGB(rx, rz));
                    
                    int r = (int)(terrainColor.getRed()* (1 - influence) + rockColor.getRed() * influence);
                    int g = (int)(terrainColor.getGreen() * (1 - influence) + rockColor.getGreen() * influence);
                    int b = (int)(terrainColor.getBlue() * (1 - influence) + rockColor.getBlue() * influence);

                    return new Color(clamp(r),clamp(g),clamp(b));
                }
            }
        }

        return terrainColor;
    }

    public int clamp(int value) {
        
        if (value > 255) {
            return 255;
        } else if (value < 0) {
            return 0;
        } else {
            return value;
        }
    }

    public double getLight(double x, double z) {

        double left  = getHeight(x - 1, z);
        double right = getHeight(x + 1, z);
    
        double up    = getHeight(x, z - 1);
        double down  = getHeight(x, z + 1);
    
        double dx = right - left;
        double dz = down - up;

        double nx = -dx;
        double ny = 2.0;
        double nz = -dz;
        double len = Math.sqrt(nx * nx + ny * ny + nz * nz);
        
        nx /= len;
        ny /= len;
        nz /= len;
        
        double sx = 0.7;
        double sy = 0.5;
        double sz = 0.3;
        double sl = Math.sqrt(sx*sx + sy*sy + sz*sz);
        
        sx /= sl;
        sy /= sl;
        sz /= sl;
        
        double light = nx * sx + ny * sy + nz * sz;
        light = 0.3 + light * 0.7;
    
        return Math.max(0.2, Math.min(1.3, light));
    }

    public void updateAttitude() {

        double cos = Math.cos(roverAttitude[0]);
        double sin = Math.sin(roverAttitude[0]);
    
        // wheel offsets
        double halfW = roverWidth / 2.0;
        double halfL = roverLength / 2.0;
    
        // Front left
        double flx = playerPosX + cos * halfL - sin * halfW;
        double flz = playerPosZ + sin * halfL + cos * halfW;
    
        // Front right
        double frx = playerPosX + cos * halfL + sin * halfW;
        double frz = playerPosZ + sin * halfL - cos * halfW;
    
        // Rear left
        double rlx = playerPosX - cos * halfL - sin * halfW;
        double rlz = playerPosZ - sin * halfL + cos * halfW;
    
        // Rear right
        double rrx = playerPosX - cos * halfL + sin * halfW;
        double rrz = playerPosZ - sin * halfL - cos * halfW;
    
        double fl = getHeight(flx, flz);
        double fr = getHeight(frx, frz);
        double rl = getHeight(rlx, rlz);
        double rr = getHeight(rrx, rrz);
    
        // Forward/back tilt
        double frontAvg = (fl + fr) * 0.5;
        double rearAvg = (rl + rr) * 0.5;
    
        roverAttitude[1]= Math.atan2(frontAvg - rearAvg, roverLength);
    
        // Side tilt
        double leftAvg = (fl + rl) * 0.5;
        double rightAvg = (fr + rr) * 0.5;
    
        roverAttitude[2] = Math.atan2(leftAvg - rightAvg, roverWidth);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int mapXExtent = getWidth();
        int mapYExtent = getHeight();

        Graphics2D g2 = (Graphics2D) g;

        // Fill entire background first
        g2.setColor(new Color(170, 110, 90));
        g2.fillRect(0, 0, mapXExtent, mapYExtent);
        
        // Full sky gradient
        GradientPaint sky = new GradientPaint(0, 0, new Color(90, 60, 50),0, mapYExtent, new Color(220, 170, 130));
        
        g2.setPaint(sky);
        g2.fillRect(0, 0, mapXExtent, mapYExtent);
        

        double eye = getHeight(playerPosX, playerPosZ) + 1;

        for (int x = 0; x < mapXExtent; x++) {

            double rayAngle = (cameraAngle - 0.35) + (x / (double) mapXExtent) * 0.7;
            double maxY = mapYExtent;

            for (int rayDistance = 1; rayDistance < 600; rayDistance++) {

                double rx = playerPosX + Math.cos(rayAngle) * rayDistance;
                double rz = playerPosZ + Math.sin(rayAngle) * rayDistance;

                double hgt = getHeight(rx, rz);

                double proj = (hgt - eye) / (rayDistance * 0.6) * 1500;
                int sy = (int)(mapYExtent / 2 - proj + pitch);

                if (sy < maxY) {

                    Color base = sampleTexture(rx, rz);
                    double light = getLight(rx, rz);

                    double fog = Math.pow(rayDistance / 500.0, 2);

                    int red = clamp((int)(base.getRed() * light * (1 - fog) + 170 * fog));

                    int green = clamp((int)(base.getGreen() * light * (1 - fog) + 110 * fog));

                    int blue = clamp((int)(base.getBlue() * light * (1 - fog) + 90 * fog));

                    g.setColor(new Color(red, green, blue));
                    g.drawLine(x, sy, x, (int)maxY);

                    maxY = sy;
                }
            }
        }
    }
}