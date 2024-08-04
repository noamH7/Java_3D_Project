package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Geometries class
 * @author Naomi and Noam
 */
class GeometriesTest {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {

        Ray ray = new Ray(new Point3D(0, 0, -1), new Vector(0, 0, 1));//

        //============ Equivalence Partitions Tests ==============

        //TC01: few geometries have Intersections points
        Geometries geometries1 = new Geometries(new Sphere(1, new Point3D(0, 0, 2)),
                new Triangle(new Point3D(0, 1, 0), new Point3D(1, 0, 0), new Point3D(2, 0, 0)),
                new Plane(new Point3D(0, 0, 4), new Vector(0, 0, 1)));
        assertEquals(3, geometries1.findIntersections(ray).size(),
                "ERROR: The ray should cut the geometries in three points (only few geometries should have Intersections points)");

        // =============== Boundary Values Tests ==================

        //TC11: The collection is empty
        Geometries geometries2 = new Geometries();
        assertNull(geometries2.findIntersections(ray), "ERROR: The collection is empty");

        //TC12: all the geometries in the collection does not not have Intersections points
        Geometries geometries3 = new Geometries(new Sphere(1, new Point3D(0, 0, -2)),
                new Triangle(new Point3D(0, 1, 0), new Point3D(1, 0, 0), new Point3D(2, 0, 0)));
        assertNull(geometries3.findIntersections(ray), "ERROR: The ray should not cut any geometry");

        //TC13: only one geometries from the collection has Intersections point/s
        Geometries geometries4 = new Geometries(new Sphere(1, new Point3D(0, 0, 2)),
                new Triangle(new Point3D(0, 1, 0), new Point3D(1, 0, 0), new Point3D(2, 0, 0)));
        assertEquals(2, geometries4.findIntersections(ray).size(), "ERROR: The ray should cut the geometries in two points");

        //TC14: all the geometries in the collection have Intersections points
        Geometries geometries5 = new Geometries(new Sphere(1, new Point3D(0, 0, 2)),
                new Plane(new Point3D(0, 0, 4), new Vector(0, 0, 1)));
        assertEquals(3, geometries5.findIntersections(ray).size(),
                "ERROR: The ray should cut the geometries in three points (all the geometries in the collection should have Intersections points)");
    }

}

