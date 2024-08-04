package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Naomi and Noam
 * Interface for geometries that will be interctioned by raies
 */

public abstract class Intersectable {
    /**
     * AABB of the object
     */
    Box _box = null;

    /**
     * Conservative Boundary Region, using AABB
     */
    public class Box {
        public final double xMax;
        public final double yMax;
        public final double zMax;
        public final double xMin;
        public final double yMin;
        public final double zMin;

        /**
         * constructor of box
         *
         * @param xMax the maximum value to x in the box
         * @param yMax the maximum value to y in the box
         * @param zMax the maximum value to z in the box
         * @param xMin the minimum value to x in the box
         * @param yMin the minimum value to y in the box
         * @param zMin the minimum value to z in the box
         */
        public Box(double xMax, double yMax, double zMax, double xMin, double yMin, double zMin) {
            this.xMax = xMax;
            this.yMax = yMax;
            this.zMax = zMax;
            this.xMin = xMin;
            this.yMin = yMin;
            this.zMin = zMin;
        }

        /**
         * default constructor of box
         */
        public Box() {
            xMax = 0;
            yMax = 0;
            zMax = 0;
            xMin = 0;
            yMin = 0;
            zMin = 0;
        }

        /**
         * check if the ray intersect the AABB
         *
         * @param ray to the box
         * @return true if the ray intersect the AABB
         */
        public boolean intersectBox(Ray ray) {
            Point3D p0 = ray.getP0();
            Point3D pDirection = ray.getDirection().getHead();

            double txMin = (xMin - p0.getX()) / pDirection.getX();
            double txMax = (xMax - p0.getX()) / pDirection.getX();
            if (txMin > txMax) {
                //swap(tMin, tMax)
                double help = txMin;
                txMin = txMax;
                txMax = help;
            }

            double tyMin = (yMin - p0.getY()) / pDirection.getY();
            double tyMax = (yMax - p0.getY()) / pDirection.getY();
            if (tyMin > tyMax) {
                // swap(tyMin, tyMax);
                double help = tyMin;
                tyMin = tyMax;
                tyMax = help;
            }

            double tzMin = (zMin - p0.getZ()) / pDirection.getZ();
            double tzMax = (zMax - p0.getZ()) / pDirection.getZ();
            if (tzMin > tzMax) {
                // swap(tzMin, tzMax);
                double help = tzMin;
                tzMin = tzMax;
                tzMax = help;
            }

            double tFar = Math.min(txMax, Math.min(tyMax, tzMax));
            double tNear = Math.max(txMin, Math.max(tyMin, tzMin));
            if (tNear > tFar)
                return false;

            return true;
        }

        /**
         * equals function
         *
         * @param o object to equals
         * @return true if o and object are equals and false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Box box = (Box) o;
            return Double.compare(box.xMax, xMax) == 0 &&
                    Double.compare(box.yMax, yMax) == 0 &&
                    Double.compare(box.zMax, zMax) == 0 &&
                    Double.compare(box.xMin, xMin) == 0 &&
                    Double.compare(box.yMin, yMin) == 0 &&
                    Double.compare(box.zMin, zMin) == 0;
        }


    }

    /**
     * calculate AABB for the geometry
     *
     * @return AABB for the geometry
     */
    public abstract Box getBox();

    /**
     * check geometries intersect point
     *
     * @param ray to the geometric
     * @return Collection of intersection points with the geometry
     */
    public List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }

    /**
     * check geometries intersect point with ray
     *
     * @param ray to the geometric
     * @return Collection of GoePoints for intersection points with the geometry
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * check geometries intersect point
     *
     * @param ray         to the geometric
     * @param maxDistance maximum range that included the intersection points
     * @return Collection of intersection points with the geometric
     */
    public abstract List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

    /**
     * A class that connects a intersection point to a geometry
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * constructor
         *
         * @param geometry that the point belongs to it.
         * @param point    intersection point.
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * equals function
         *
         * @param o object to equals
         * @return true if o and object are equals and false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }
    }


}
