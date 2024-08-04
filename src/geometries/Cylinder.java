package geometries;

import primitives.Ray;

/**
 * final cylinder
 *
 * @author Noam and Naomi
 */
public class Cylinder extends Tube {
    /**
     * height of cylinder
     */
    final double height;

    /**
     * constructor
     *
     * @param axisRay ray axis of cylinder
     * @param radius  of cylinder
     * @param height  of cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * getter
     *
     * @return height of cylinder
     */
    public double getHeight() {
        return height;
    }

    /**
     * string of object cylinder
     *
     * @return string of object
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + _axisRay.toString() +
                ", radius=" + _radius +
                '}';
    }
}
