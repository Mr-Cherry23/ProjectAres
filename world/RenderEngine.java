import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class RenderEngine extends JPanel {
    SensorPanel sensors;


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
            ConsolePanel.log("[RenderEngine] " + error.toString());
            System.exit(1);
        }
        
        setPreferredSize(new Dimension(1280, 720));

        features.add(new Rock(344, 1670, 1));
        features.add(new Rock(850, 1670, 3));
        features.add(new Rock(330, 234, 5));
        features.add(new Rock(345, 312, 8));
        features.add(new Rock(230, 1670, 1));
        features.add(new Rock(464, 123, 1));
        features.add(new Rock(100, 93, 5));
        features.add(new Rock(670, 531, 1));
        features.add(new Rock(865, 1670, 1));
        features.add(new Rock(234, 489, 1));
        features.add(new Rock(556, 500, 1));
        features.add(new Rock(546, 1594, 1));
        features.add(new Rock(487, 1385, 1));
        features.add(new Rock(850, 231, 1));
        features.add(new Rock(1036, 853, 1));
        features.add(new Rock(214, 1863, 1));
        // Additional rocks to densify the terrain (approx. 50)
        features.add(new Rock(150, 420, 2));
        features.add(new Rock(480, 210, 3));
        features.add(new Rock(920, 640, 2));
        features.add(new Rock(1250, 300, 4));
        features.add(new Rock(1320, 890, 2));
        features.add(new Rock(420, 980, 3));
        features.add(new Rock(780, 1200, 5));
        features.add(new Rock(1020, 140, 2));
        features.add(new Rock(1560, 720, 3));
        features.add(new Rock(1880, 420, 1));
        features.add(new Rock(60, 60, 2));
        features.add(new Rock(210, 365, 1));
        features.add(new Rock(310, 560, 2));
        features.add(new Rock(400, 760, 3));
        features.add(new Rock(510, 860, 1));
        features.add(new Rock(610, 960, 4));
        features.add(new Rock(710, 1060, 2));
        features.add(new Rock(810, 1160, 3));
        features.add(new Rock(910, 1260, 2));
        features.add(new Rock(1010, 1360, 5));
        features.add(new Rock(1110, 1460, 1));
        features.add(new Rock(1210, 1560, 2));
        features.add(new Rock(1310, 1660, 3));
        features.add(new Rock(1410, 1760, 2));
        features.add(new Rock(1510, 1860, 4));
        features.add(new Rock(1610, 1960, 1));
        features.add(new Rock(1710, 180, 3));
        features.add(new Rock(1810, 280, 2));
        features.add(new Rock(1910, 380, 1));
        features.add(new Rock(200, 980, 2));
        features.add(new Rock(340, 120, 3));
        features.add(new Rock(470, 240, 1));
        features.add(new Rock(590, 360, 2));
        features.add(new Rock(630, 480, 1));
        features.add(new Rock(740, 590, 3));
        features.add(new Rock(820, 720, 2));
        features.add(new Rock(930, 840, 4));
        features.add(new Rock(1040, 960, 3));
        features.add(new Rock(1150, 1080, 2));
        features.add(new Rock(1260, 1200, 5));
        features.add(new Rock(1370, 1320, 1));
        features.add(new Rock(1480, 1440, 2));
        features.add(new Rock(1590, 1560, 3));
        features.add(new Rock(1700, 1680, 2));
        features.add(new Rock(1810, 1790, 4));
        features.add(new Rock(1920, 1910, 1));
        features.add(new Rock(50, 1900, 3));
        features.add(new Rock(300, 1740, 2));
        features.add(new Rock(880, 155, 2));
        features.add(new Rock(1430, 95, 3));

        Timer timer = new Timer(16, e -> {
            repaint();
            updateAttitude();
        });
        
        timer.start();
    }

    // Expose map and player position for mission checks
    public int getMapPixelWidth() {
        return (heightMap != null) ? heightMap.getWidth() : 0;
    }

    public int getMapPixelHeight() {
        return (heightMap != null) ? heightMap.getHeight() : 0;
    }

    public double getPlayerPosX() {
        return playerPosX;
    }

    public double getPlayerPosZ() {
        return playerPosZ;
    }

    void setSensors(SensorPanel sensors) {
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
        // consume a small amount of power for turning
        double turnCost = 0.25;
        if (GameState.powerManager != null) {
            if (!GameState.powerManager.consume(turnCost)) {
                ConsolePanel.log("[RenderEngine] Not enough power to turn left");
                return;
            }
        }
        roverAttitude[0] -= Math.toRadians(15);
        cameraAngle -= Math.toRadians(15);
        sensors.updateReadings();
    }

    public void turnRight() {
        double turnCost = 0.25;
        if (GameState.powerManager != null) {
            if (!GameState.powerManager.consume(turnCost)) {
                ConsolePanel.log("[RenderEngine] Not enough power to turn right");
                return;
            }
        }
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
        double moveCost = 1.0; // power per move
        if (GameState.powerManager != null) {
            if (!GameState.powerManager.consume(moveCost)) {
                ConsolePanel.log("[RenderEngine] Not enough power to move forward");
                return;
            }
        }
        double speed = 5;
        playerPosX += Math.cos(roverAttitude[0]) * speed;
        playerPosZ += Math.sin(roverAttitude[0]) * speed;
        sensors.updateReadings();
    }

    public void moveBackward() {
        double moveCost = 1.0; // power per move
        if (GameState.powerManager != null) {
            if (!GameState.powerManager.consume(moveCost)) {
                ConsolePanel.log("[RenderEngine] Not enough power to move backward");
                return;
            }
        }
        double speed = 5;
        playerPosX -= Math.cos(roverAttitude[0]) * speed;
        playerPosZ -= Math.sin(roverAttitude[0]) * speed;
        sensors.updateReadings();
    }

    public void resetPosition() {
        playerPosX = heightMap.getWidth() / 2.0;
        playerPosZ = heightMap.getHeight() / 2.0;
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

        for (Feature feature : features) {

            if (feature instanceof Rock) {

                Rock rock = (Rock) feature;

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