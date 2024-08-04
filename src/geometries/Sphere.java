package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Ball in 3D
 * @author Noam and Naomi
 */
public class Sphere extends Geometry {
    /**
     * center point of sphere
     */
    final Point3D _center;
    /**
     * radius of sphere
     */
    final double _radius;

    /**
     * ctor
     * @param radius of sphere
     * @param center  point of sphere
     */
    public Sphere(double radius, Point3D center) {
        _center = center;
        _radius = radius;
    }

    /**
     * getter
     * @return center point of sphere
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * getter
     * @return radius of sphere
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * calculate AABB for the sphere
     *
     * @return AABB for the sphere
     */
    @Override
    public Box getBox() {
        // create box just one time
        if(_box==null) {
            _box= new Box(_center.getX() + _radius,
                    _center.getY() + _radius,
                    _center.getZ() + _radius,
                    _center.getX() - _radius,
                    _center.getY() - _radius,
                    _center.getZ() - _radius);
        }
        return _box;
    }

    /**
     * calculate normal to point in sphere
     * @param point on sphere
     * @return normal to sphere
     */
    @Override
    public Vector getNormal(Point3D point) {
        return (point.subtract(_center)).normalize();
    }

    /**
     * string of object sphere
     * @return string of object
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center.toString() +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * check sphere intersect point
     * @param ray         to the sphere
     * @param maxDistance maximum range that included the intersection points
     * @return collection of intersection points with the sphere
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D p0 = ray.getP0();
        Vector v = ray.getDirection();
        double tm = 0;
        double d = 0;
        if (!p0.equals(_center)) {
            Vector u = _center.subtract(p0);//the vector from the center of the sphere to the head of the ray
            tm = alignZero(v.dotProduct(u));// the projection of u on v
            d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));// the distance between the line of the ray to the center of the sphere
        }
        if (d >= _radius)// d is bigger than the radius of the sphere- the ray doesn't touch the sphere
            return null;
        double th = alignZero(Math.sqrt(_radius * _radius - d * d));// the distance between intersection points(if they are exist) to the center of the chord
        double t1 = tm + th;//the "distance" between the head of the ray to intersection point 1
        double t2 = tm - th;//the "distance" between the head of the ray to intersection point 2
        //intersection points are exist only if the "distance" are positive.
        boolean validT1 = alignZero(t1 - maxDistance) <= 0;
        boolean validT2 = alignZero(t2 - maxDistance) <= 0;
        if (t1 > 0 && t2 > 0 && validT1 && validT2) {
            Point3D p1 = ray.getPoint(t1);
            Point3D p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
        }
        if (t1 > 0 && validT1) {
            Point3D p3 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, p3));
        }
        if (t2 > 0 && validT2) {
            Point3D p4 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p4));
        }
        return null;
    }
}
