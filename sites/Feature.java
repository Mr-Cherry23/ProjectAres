import java.awt.*;

public abstract class Feature {

    double x;
    double z;

    public Feature(double x, double z) {
        this.x = x;
        this.z = z;
    }

    public abstract void render(Graphics g, RenderEngine engine);

    public double getX() {
        return x;
    }

    public double getZ() {
        return z;
    }
}