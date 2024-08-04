package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Collection of geometries
 *
 * @author Noam and Naomi
 */
public class Geometries extends Intersectable {
    /**
     * list of geometries
     */
    private List<Intersectable> _geometries = null;
    /**
     * flag to use BVH improvement
     */
    private boolean _bvh = false;

    /**
     * turn on BVH improvement
     *
     * @return the object for linking
     */
    public Geometries setBvh() {
        _bvh = true;
        return this;
    }

    /**
     * Empty constructor
     */
    public Geometries() {
        _geometries = new LinkedList<Intersectable>();
    }

    /**
     * Constructor with addition
     */
    public Geometries(Intersectable... geometries) {
        _geometries = new LinkedList<Intersectable>();
        add(geometries);
    }

    /**
     * Addition to the collection
     *
     * @param geometries list for addition
     */
    public void add(Intersectable... geometries) {
        _geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * calculate AABB for the geometries collection
     *
     * @return AABB for the geometries collection
     */
    @Override
    public Box getBox() {
        if (_box == null) {
            double xMax = Double.NEGATIVE_INFINITY;
            double yMax = Double.NEGATIVE_INFINITY;
            double zMax = Double.NEGATIVE_INFINITY;
            double xMin = Double.POSITIVE_INFINITY;
            double yMin = Double.POSITIVE_INFINITY;
            double zMin = Double.POSITIVE_INFINITY;
            for (Intersectable geo : _geometries) {
                Box box = geo.getBox();
                if (box.xMax > xMax)
                    xMax = box.xMax;
                if (box.yMax > yMax)
                    yMax = box.yMax;
                if (box.zMax > zMax)
                    zMax = box.zMax;
                if (box.xMin < xMin)
                    xMin = box.xMin;
                if (box.yMin < yMin)
                    yMin = box.yMin;
                if (box.zMin < zMin)
                    zMin = box.zMin;
            }
            _box = new Box(xMax, yMax, zMax, xMin, yMin, zMin);
        }
        return _box;
    }

    /**
     * check geometries intersect point with ray
     *
     * @param ray to the geometric
     * @return Collection of intersection points with the geometric
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        if (!_bvh) {
            LinkedList<GeoPoint> result = null;
            for (Intersectable geo : _geometries) {
                List<GeoPoint> points = geo.findGeoIntersections(ray, maxDistance);//find intersections point to every single geometry.
                if (points != null) {// intersections points are exist.
                    if (result == null) {//if we didn't insert intersections point yet.
                        result = new LinkedList<GeoPoint>();
                    }
                    result.addAll(points);// copy all the intersections points to the general list.
                }
            }
            return result;
        }
        return findGeoIntersectionsBVH(ray, maxDistance);
    }

    /**
     * check geometries intersect point with BVH improvement
     *
     * @param ray         to the geometric
     * @param maxDistance
     * @return Collection of intersection points with the geometric
     */
    private List<GeoPoint> findGeoIntersectionsBVH(Ray ray, double maxDistance) {
        LinkedList<GeoPoint> result = null;
        //just if the ray intersect the box we will check the intersect points with the geometries
        if (this.getBox().intersectBox(ray)) {
            for (Intersectable geo : _geometries) {
                //just if the ray intersect the sub box we will check the intersect points with the geometry
                if (geo.getBox().intersectBox(ray)) {
                    List<GeoPoint> points = geo.findGeoIntersections(ray, maxDistance);//find intersections point to every single geometry.
                    if (points != null) {// intersections points are exist.
                        if (result == null) {//if we didn't insert intersections point yet.
                            result = new LinkedList<GeoPoint>();
                        }
                        result.addAll(points);// copy all the intersections points to the general list.
                    }
                }
            }
        }
        return result;
    }

    /**
     * not implement
     */
    public void automaticHierarchy() {

    }
}

