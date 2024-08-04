package primitives;

import java.util.LinkedList;

import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * a basic ray
 * @author Noam and Naomi
 */
public class Ray {
    /**
     * the size of rays shifting for shifted rays
     */
    private static final double DELTA = 0.1;

    /**
     * start of ray
     */
    final Point3D _p0;

    /**
     * direction vector of the ray (unit vector)
     */
    final Vector _direction;

    /**
     * ctor of ray
     *
     * @param p0 start point of ray
     * @param direction direction vector of the ray
     */
    public Ray(Point3D p0, Vector direction) {
        _p0 = p0;
        _direction = direction.normalized();
    }

    /**
     * ctor of shifted ray
     * @param point start point of ray
     * @param n vector to shifting
     * @param direction direction vector of the ray
     */
    public Ray(Point3D point, Vector n, Vector direction) {
        Vector delta = n.scale(n.dotProduct(direction) > 0 ? DELTA : - DELTA);
        _p0 = point.add(delta);
        _direction = direction;
    }

    /**
     * getter
     * @return start point of ray
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * getter
     * @return direction vector of the ray
     */
    public Vector getDirection() {
        return _direction;
    }

    /**
     * find point on the ray using distance from start of ray
     * @param t distance from start ray to the wanted point
     * @return wanted point
     */
    public Point3D getPoint(double t) {
        return _p0.add(_direction.scale(t));
    }

    /**
     * find closest point to the head of ray
     * @param points list of 3D points
     * @return the point which closest to head of ray
     */
    public Point3D findClosestPoint(List<Point3D> points) {
        Point3D nearPoint = null;
        if (points != null) {
            //Start with a maximum distance
            // (so if there are points on the list, the next distance will be smaller)
            double minimumDistance = Double.POSITIVE_INFINITY;
            //Go through the list and search the closest point
            for (Point3D p : points) {
                double distance = _p0.distance(p);
                if (distance < minimumDistance) {
                    minimumDistance = distance;
                    nearPoint = p;
                }
            }
        }
        return nearPoint;
    }

    /**
     * find closest point with its Geometry to the head of ray
     * @param points list of geoPoints
     * @return the closest
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        GeoPoint nearPoint = null;
        if (points != null) {
            //Start with a maximum distance
            // (so if there are points on the list, the next distance will be smaller)
            double minimumDistance = Double.POSITIVE_INFINITY;
            //Go through the list and search the closest point
            for (GeoPoint geoPoint : points) {
                double distance = _p0.distance(geoPoint.point);
                if (distance < minimumDistance) {
                    minimumDistance = distance;
                    nearPoint = geoPoint;
                }
            }
        }
        return nearPoint;
    }

    /**
     * string of object ray
     * @return string of object
     */
    @Override
    public String toString() {
        return "Ray{" +
                "_p0=" + _p0.toString() +
                ", _direction=" + _direction.toString() +
                '}';
    }

    /**
     * equals 2 rays
     *
     * @param o object to compare
     * @return true if they are equals else return false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _direction.equals(ray._direction);
    }


}
