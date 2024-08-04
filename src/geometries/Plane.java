package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * plane in the space
 *
 * @author Noam and Naomi
 */
public class Plane extends Geometry {
    /**
     * point on the plane
     */
    final Point3D _q0;

    /**
     * the normal vector to the plane
     */
    final Vector _normal;

    /**
     * ctor, get point and normal vector
     *
     * @param q0     point on the plane
     * @param normal vector that orthogonal to the plane
     */
    public Plane(Point3D q0, Vector normal) {
        _q0 = q0;
        _normal = normal;
    }

    /**
     * ctor get 3 points and calculate the normal
     *
     * @param p1 point including in the plane
     * @param p2 point including in the plane
     * @param p3 point including in the plane
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        _normal = (v1.crossProduct(v2)).normalize();
    }


    /**
     * getter
     *
     * @return point on plane
     */
    public Point3D getQ0() {
        return _q0;
    }

    /**
     * calculate the normal to plane in point
     *
     * @param point on the plan
     * @return normal of plane (the same normal for all point)
     */
    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }

    /**
     * calculate AABB box for plan (not implemented)
     *
     * @return box (=null)
     */
    @Override
    public Box getBox() {
        return null;
    }

    /**
     * string of object Plane
     *
     * @return string of object
     */
    @Override
    public String toString() {
        return "Plane{" +
                "_q0=" + _q0.toString() +
                ", _normal=" + _normal.toString() +
                '}';
    }

    /**
     * find points intersection of ray with plane
     *
     * @param ray         to the plane
     * @param maxDistance maximum range that included the intersection points
     * @return points intersection of ray with plane
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D p0 = ray.getP0();
        Vector v = ray.getDirection();
        //if ray start on plane
        if (_q0.equals(p0)) {
            return null;
        }
        double nv = _normal.dotProduct(v);
        if (isZero(nv)) {//the ray parallel to the plane
            return null;
        }
        double t = alignZero((_normal.dotProduct(_q0.subtract(p0))) / nv);
        if (t > 0 && alignZero(t - maxDistance) <= 0) {
            Point3D p = ray.getPoint(t);
            return List.of(new GeoPoint(this, p));
        }
        return null;
    }
}
