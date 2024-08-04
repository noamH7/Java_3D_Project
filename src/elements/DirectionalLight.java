package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for directional light (like the sun)
 * * @author Noam and Naomi
 */
public class DirectionalLight extends Light implements LightSource {
    /**
     * direction of light
     */
    private Vector _direction;

    /**
     * constructor
     *
     * @param intensity of the light
     * @param direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    /**
     * @param p point on object
     * @return intensity light in this point
     */
    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    /**
     * @param p point on object
     * @return the vector from the light source to the object
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    /**
     * @param point point on object
     * @return the distance between the directional light to the object (infinity distance)
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
