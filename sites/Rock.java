import java.awt.*;

public class Rock extends Feature {

    double size;

    public Rock(double x, double z, double size) {
        super(x, z);
        this.size = size;
    }

    @Override
    public void render(Graphics g, RenderEngine engine) {

        double dx = x - engine.playerPosX;
        double dz = z - engine.playerPosZ;

        double distance = Math.sqrt(dx * dx + dz * dz);

        // angle from player to rock
        double angleToRock = Math.atan2(dz, dx);

        double relativeAngle = angleToRock - engine.angle;

        // normalize angle
        while (relativeAngle < -Math.PI) relativeAngle += Math.PI * 2;
        while (relativeAngle > Math.PI) relativeAngle -= Math.PI * 2;

        // cull behind player
        if (Math.abs(relativeAngle) > 0.5)
            return;

        // project to screen X
        int screenX = (int)(
            engine.getWidth() / 2 +
            Math.tan(relativeAngle) * engine.getWidth()
        );

        // terrain height
        double terrainHeight = engine.getHeight(x, z);

        double eyeLevel = engine.getHeight(
            engine.playerPosX,
            engine.playerPosZ
        ) + 10;

        double projectedHeight =
            (terrainHeight - eyeLevel) / distance * 1500;

        int screenY = (int)(
            engine.getHeight() / 2 - projectedHeight
        );

        // perspective scaling
        int rockSize = (int)(size / distance * 1200);

        if (rockSize < 2)
            return;

        // shadow
        g.setColor(new Color(40, 20, 10));
        g.fillOval(
            screenX - rockSize / 2,
            screenY - rockSize / 4,
            rockSize,
            rockSize / 2
        );

        // rock body
        g.setColor(new Color(120, 90, 70));

        g.fillOval(
            screenX - rockSize / 2,
            screenY - rockSize,
            rockSize,
            rockSize
        );

        // highlight
        g.setColor(new Color(170, 140, 110));

        g.fillOval(
            screenX - rockSize / 4,
            screenY - rockSize + rockSize / 5,
            rockSize / 3,
            rockSize / 3
        );
    }
}