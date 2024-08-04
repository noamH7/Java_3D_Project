package geometries;
/**
 * Unit tests for geometries.Triangle class
 *
 * @author Naomi and Noam
 */

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    
    /**
     * Test method for {@link Triangle#getNormal(primitives.Point3D)} }.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: the calculation of the normal to a triangle is correct
        Triangle triangle = new Triangle(new Point3D(0, 1, 1), new Point3D(0, 0, 2), new Point3D(0, 0, 3));
        Vector v1 = new Vector(1, 0, 0);
        Vector v3 = new Vector(-1, 0, 0);
        Vector v2 = triangle.getNormal(new Point3D(0, 0.5, 2));
        if (!v1.equals(v2) && !v3.equals(v2))
            fail("ERROR: wrong normal");

    }

    /**
     * Test method for {@link Triangle#findIntersections(Ray)} }.
     */
    @Test
    void findIntsersections() {

        Point3D a = new Point3D(0, 0, 5);
        Point3D b = new Point3D(0, 5, 0);
        Point3D c = new Point3D(0, 0, 10);

        Triangle tr = new Triangle(a, b, c);

        //============ Equivalence Partitions Tests ==============

        //TC01: The ray intersects with the plane inside triangle(1 point)
        Ray ray1 = new Ray(new Point3D(3, 1, 7), new Vector(-1, 0, 0));
        assertEquals(1, tr.findIntersections(ray1).size(),
                "ERROR: The ray should cut the Triangle in one points");

        assertEquals(new Point3D(0, 1, 7), tr.findIntersections(ray1).get(0),
                "ERROR: wrong point");

        //TC02: The ray intersects with the plane outside against edge(0 point)
        Ray ray2 = new Ray(new Point3D(5, 2, 1), new Vector(-1, 0, 0));
        assertNull( tr.findIntersections(ray2),
                "ERROR: The ray should not cut the triangle");

        //TC03: The ray intersects with the plane outside against vertex(0 point)
        Ray ray3 = new Ray(new Point3D(9, -2, 3), new Vector(-1, 0, 0));
        assertNull( tr.findIntersections(ray3),
                "ERROR: The ray should not cut the triangle");


        // =============== Boundary Values Tests ==================

        //TC11: The ray intersects with the plane on edge(0 point)
        Ray ray4 = new Ray(new Point3D(-3, 0, 8), new Vector(1, 0, 0));
        assertNull( tr.findIntersections(ray4),
                "ERROR: The ray should not cut the triangle");

        //TC12: The ray intersects with the plane in vertex(0 point)
        Ray ray5 = new Ray(new Point3D(6, 0, 10), new Vector(-1, 0, 0));
        assertNull( tr.findIntersections(ray5),
                "ERROR: The ray should not cut the triangle");

        //TC13: The ray intersects with the plane on edge's continuation(0 point)
        Ray ray6 = new Ray(new Point3D(5, 0, 2), new Vector(-1, 0, 0));
        assertNull( tr.findIntersections(ray6),
                "ERROR: The ray should not cut the triangle");


    }


}