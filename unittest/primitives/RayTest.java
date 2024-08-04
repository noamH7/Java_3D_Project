package primitives;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 *
 * @author Naomi and Noam
 */
class RayTest {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(java.util.List<Point3D>)}.
     */
    @Test
    void findClosestPoint() {

        Point3D p1 = new Point3D(0, 2, 2);
        Point3D p2 = new Point3D(0, 3, 0);
        Point3D p3 = new Point3D(0, 0, 10);
        Point3D p4 = new Point3D(0, 0, 1);

        List<Point3D> points = List.of(p1, p2, p3, p4);

        // ============ Equivalence Partitions Tests ==============

        // TC01: A point in the middle of the list is the closest
        assertEquals(p3, new Ray(new Point3D(0, -1, 9), new Vector(0, 0, 1)).findClosestPoint(points), "ERROR: wrong closest point");

        // =============== Boundary Values Tests ==================

        // TC10: The list is empty31
        assertNull(new Ray(new Point3D(0, -1, 2), new Vector(0, 0, 1)).findClosestPoint(new ArrayList<>()), "ERROR: the list is empty");

        // TC11: The first point in the list is the closest
        assertEquals(p1, new Ray(new Point3D(0, 1, 3), new Vector(0, 0, 1)).findClosestPoint(points), "ERROR: wrong closest point");

        // TC12: The last point in the list is the closest
        assertEquals(p4, new Ray(new Point3D(0, -1, 2), new Vector(0, 0, 1)).findClosestPoint(points), "ERROR: wrong closest point");


    }

    /**
     * Test method for {@link primitives.Ray#findClosestGeoPoint(java.util.List<GeoPoint>)}
     */
    @Test
    void getClosestGeoPoint() {
        GeoPoint gp1 = new GeoPoint(new Plane(new Point3D(0, 2, 2), new Vector(0, 0, 1)), new Point3D(0, 2, 2));
        GeoPoint gp2 = new GeoPoint(new Polygon(new Point3D(3, 0, 0), new Point3D(-3, 0, 0),
                new Point3D(-3, 6, 0), new Point3D(3, 6, 0)), new Point3D(0, 3, 0));
        GeoPoint gp3 = new GeoPoint(new Sphere(3, new Point3D(0, 0, 10)), new Point3D(0, 0, 10));
        GeoPoint gp4 = new GeoPoint(new Triangle(new Point3D(-5, 0, 0), new Point3D(-5, 0, 2), new Point3D(5, 0, 1)),
                new Point3D(0, 0, 1));

        List<GeoPoint> points = List.of(gp1, gp2, gp3, gp4);

        // ============ Equivalence Partitions Tests ==============

        // TC01: A point in the middle of the list is the closest
        assertEquals(gp3, new Ray(new Point3D(0, -1, 9), new Vector(0, 0, 1)).findClosestGeoPoint(points), "ERROR: wrong closest point");

        // =============== Boundary Values Tests ==================

        // TC10: The list is empty31
        assertNull(new Ray(new Point3D(0, -1, 2), new Vector(0, 0, 1)).findClosestGeoPoint(new ArrayList<>()), "ERROR: the list is empty");

        // TC11: The first point in the list is the closest
        assertEquals(gp1, new Ray(new Point3D(0, 1, 3), new Vector(0, 0, 1)).findClosestGeoPoint(points), "ERROR: wrong closest point");

        // TC12: The last point in the list is the closest
        assertEquals(gp4, new Ray(new Point3D(0, -1, 2), new Vector(0, 0, 1)).findClosestGeoPoint(points), "ERROR: wrong closest point");


    }

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}
     */
    @Test
    void getPoint() {
        Ray ray= new Ray(new Point3D(0,0,1),new Vector(0,0,1));
        assertEquals(ray.getPoint(3), new Point3D(0,0,4), "ERROR: wrong point");

    }
}