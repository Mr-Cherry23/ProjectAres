import java.awt.*;

public class ScienceSite extends Feature {

    public ScienceSite(double x, double z) {
        super(x, z);
    }

    @Override
    public void render(Graphics g, RenderEngine engine) {

        // convert world → screen
        double dx = x - engine.playerPosX;
        double dz = z - engine.playerPosZ;

        double dist = Math.sqrt(dx * dx + dz * dz);

        double angleToFeature = Math.atan2(dz, dx);

        double relativeAngle = angleToFeature - engine.angle;

        // horizontal screen position
        int screenX = (int)(
            engine.getWidth() / 2 +
            Math.tan(relativeAngle) * engine.getWidth()
        );

        // terrain height
        double h = engine.getHeight(x, z);

        double eye = engine.getHeight(
            engine.playerPosX,
            engine.playerPosZ
        ) + 10;

        double proj = (h - eye) / dist * 1500;

        int screenY = (int)(
            engine.getHeight() / 2 - proj
        );

        int size = (int)(300 / dist);

        if (size < 2) size = 2;

        g.setColor(Color.CYAN);

        g.fillOval(
            screenX - size / 2,
            screenY - size / 2,
            size,
            size
        );
    }
}