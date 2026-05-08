public abstract class Feature {

    double x;
    double z;

    public Feature(double x, double z) {
        this.x = x;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getZ() {
        return z;
    }
}