package geometries;
/**
 * Unit tests for geometries.Polygon class
 *
 * @author Dan
 */

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.Assert.*;

class PolygonTest {

    /**
     * Test method for
     * {@link geometries.Polygon#Polygon(Point3D...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new
                    Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {
        }

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {
        }

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {
        }

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {
        }

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {
        }

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {
        }

    }

    /**
     * Test method for {@link Polygon#getNormal(primitives.Point3D)} }.
     */

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)), "Bad normal to triangle");
    }

    @Test
    /**
     *  Test method for {@link Polygon#findIntersections(Ray)} }.
     *  @author Naomi and noam
     */
    void findIntsersections() {

        Point3D a = new Point3D(0, 0, 5);
        Point3D b = new Point3D(0, 5, 0);
        Point3D c = new Point3D(0, 10, 0);
        Point3D d = new Point3D(0, 0, 10);
        Polygon polygon = new Polygon(a, b, c, d);

        //============ Equivalence Partitions Tests ==============

        //TC01: The ray intersects with the plane inside polygon(1 point)
        Ray ray1 = new Ray(new Point3D(3, 1, 7), new Vector(-1, 0, 0));
        assertEquals(1, polygon.findIntersections(ray1).size(),
                "ERROR: The ray should cut the polygon in one points");

        assertEquals(new Point3D(0, 1, 7), polygon.findIntersections(ray1).get(0),
                "ERROR: wrong point");


        //TC02: The ray intersects with the plane outside against edge(0 point)
        Ray ray2 = new Ray(new Point3D(5, 2, 1), new Vector(-1, 0, 0));
        assertNull(polygon.findIntersections(ray2),
                "ERROR: The ray should not cut the polygon");

        //TC03: The ray intersects with the plane outside against vertex(0 point)
        Ray ray3 = new Ray(new Point3D(9, -2, 3), new Vector(-1, 0, 0));
        assertNull(polygon.findIntersections(ray3),
                "ERROR: The ray should not cut the polygon");


        // =============== Boundary Values Tests ==================

        //TC11: The ray intersects with the plane on edge(0 point)
        Ray ray4 = new Ray(new Point3D(-3, 0, 8), new Vector(1, 0, 0));
        assertNull(polygon.findIntersections(ray4),
                "ERROR: The ray should not cut the polygon");

        //TC12: The ray intersects with the plane in vertex(0 point)
        Ray ray5 = new Ray(new Point3D(6, 0, 10), new Vector(-1, 0, 0));
        assertNull(polygon.findIntersections(ray5),
                "ERROR: The ray should not cut the polygon");

        //TC13: The ray intersects with the plane on edge's continuation(0 point)
        Ray ray6 = new Ray(new Point3D(5, 0, 2), new Vector(-1, 0, 0));
        assertNull(polygon.findIntersections(ray6),
                "ERROR: The ray should not cut the polygon");


    }
}