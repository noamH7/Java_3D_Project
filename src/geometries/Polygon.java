package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.*;

import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this._vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3)
            return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal(null);

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign
        // the polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    /**
     *
     * calculate AABB for the polygon
     * @return AABB for the polygon
     * @author Noam and Naomi
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
            for (Point3D point : _vertices) {
                double x = point.getX();
                double y = point.getY();
                double z = point.getZ();
                if (x > xMax)
                    xMax = x;
                if (y > yMax)
                    yMax = y;
                if (z > zMax)
                    zMax = z;
                if (x < xMin)
                    xMin = x;
                if (y < yMin)
                    yMin = y;
                if (z < zMin)
                    zMin = z;
            }
            _box = new Box(xMax, yMax, zMax, xMin, yMin, zMin);
        }
        return _box;
    }

    /**
     * calculate normal to point on polygon
     * @param point on polygon
     * @return normal to polygon
     */
    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal(point);
    }

    /**
     * check polygon intersect point
     * @param ray         to the polygon
     * @param maxDistance maximum range that included the intersection points
     * @return collection of intersection points with the polygon
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> intersect = _plane.findGeoIntersections(ray, maxDistance);
        if (intersect == null)//if the ray does not intersect the plane there are no intersection points
            return null;
        intersect.get(0).geometry = this;
        Point3D p0 = ray.getP0();
        Vector v = ray.getDirection();
        ArrayList<Vector> vectors = new ArrayList<Vector>(_vertices.size());
        ArrayList<Vector> normals = new ArrayList<Vector>(_vertices.size());
        for (Point3D vertex : _vertices) {// create vector to every single edge
            vectors.add(vertex.subtract(p0));
        }
        for (int i = 0; i < vectors.size() - 1; ++i) {// find the normal to the plane that created from any pair of vectors
            normals.add(vectors.get(i).crossProduct(vectors.get(i + 1)).normalize());
        }
        normals.add(vectors.get(vectors.size() - 1).crossProduct(vectors.get(0)).normalize());
        // the ray cut the polygon if the sign of all dot product v*Ni are the same
        double first = alignZero(normals.get(0).dotProduct(v));
        if (first > 0) {
            for (int i = 1; i < normals.size(); ++i) {
                if (alignZero(normals.get(i).dotProduct(v)) <= 0)
                    return null;
            }
            return intersect;
        }
        if (first < 0) {
            for (int i = 1; i < normals.size(); ++i) {
                if (alignZero(normals.get(i).dotProduct(v)) >= 0)
                    return null;
            }
            return intersect;
        }
        //if first = 0
        return null;
    }

}

