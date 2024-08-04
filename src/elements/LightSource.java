package elements;

import primitives.*;

/**
 * interface for Light Source
 * @author Noam and Naomi
 */
public interface LightSource {
    /**
     * getter
     * @param p point on object
     * @return intensity light in this point
     */
    public Color getIntensity(Point3D p);

    /**
     * @param p point on object
     * @return the vector from the light source to the object
     */
    public Vector getL(Point3D p);

    /**
     *
     * @param point point on object
     * @return the distance between the light source to the object
     */
    double getDistance(Point3D point);
}
