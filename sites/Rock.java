public class Rock extends Feature {

    double radius;
    double height;

    public Rock(double x, double z, double radius, double height) {

        super(x, z);

        this.radius = radius;
        this.height = height;
    }
}