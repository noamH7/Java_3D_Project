package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.*;

/**
 * class for point light
 * @author Noam and Naomi
 */
public class PointLight extends Light implements LightSource {

    /**
     * the location of the light
     */
    private Point3D _position;

    /**
     * 3 factors for attenuation with distance
     */
    private double _kC = 1;
    private double _kL = 0;
    private double _kQ = 0;

    /**
     * constructor
     * @param intensity of the light
     * @param position  is the location of the light
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * setter
     * @param kC factor for attenuation with distance
     * @return the object
     */
    public PointLight setKC(double kC) {
        _kC = kC;
        return this;
    }

    /**
     * setter
     * @param kL factor for attenuation with distance
     * @return the object (changing method)
     */
    public PointLight setKl(double kL) {
        _kL = kL;
        return this;
    }

    /**
     * setter
     * @param kQ factor for attenuation with distance
     * @return the object (changing method)
     */
    public PointLight setKq(double kQ) {
        _kQ = kQ;
        return this;
    }

    /**
     * @param p point on object
     * @return intensity light in this point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double distance = _position.distance(p);
        return _intensity.reduce(alignZero(_kC+_kL*distance+_kQ*distance*distance));
    }

    /**
     * @param p point on object
     * @return the vector from the light source to the object
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalized();
    }

    /**
     *
     * @param point point on object
     * @return the distance between the point light to the object
     */
    @Override
    public double getDistance(Point3D point) {
        return point.distance(_position);
    }
}
