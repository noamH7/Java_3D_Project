package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/**
 * infinity cylinder
 *
 * @author Noam and Naomi
 */
public class Tube extends Geometry {

    /**
     * ray axis of tube
     */
    final Ray _axisRay;
    /**
     * radius of tube
     */
    final double _radius;

    /**
     * ctor
     *
     * @param axisRay axis of tube
     * @param radius  of tube
     */
    public Tube(Ray axisRay, double radius) {
        this._axisRay = axisRay;
        this._radius = radius;
    }

    //getters

    /**
     * getter
     *
     * @return axis of tube
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * getter
     *
     * @return radius of tube
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * calculate AABB box for tube (not implemented)
     *
     * @return box (=null)
     */
    @Override
    public Box getBox() {
        return null;
    }

    /**
     * calculate normal to point on tube
     *
     * @param point on tube
     * @return normal to tube
     */
    @Override
    public Vector getNormal(Point3D point) {
        //the vector created from the head of the ray of the tube to the point
        Vector p_p0 = point.subtract(_axisRay.getP0());
        //
        double t = _axisRay.getDirection().dotProduct(p_p0);
        if (!isZero(t)) {
            Point3D o = _axisRay.getP0().add(_axisRay.getDirection().scale(t));
            return (point.subtract(o)).normalize();
        }
        return (p_p0).normalize();
    }

    /**
     * string of object tube
     *
     * @return string of object
     */
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + _axisRay.toString() +
                ", radius=" + _radius +
                '}';
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return null;
    }
}
