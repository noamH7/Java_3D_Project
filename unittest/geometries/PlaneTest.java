package geometries;
/**
 * Unit tests for geometries.Plane class
 * @author Naomi and Noam
 */


import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;


class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#Plane(Point3D, Point3D, Point3D)}
     */

    @Test
    public void testConstructor() {

        // =============== Boundary Values Tests ==================

        // TC01: Collocated points
        try {
            Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(1, 0, 0), new Point3D(2, 9, 8));
            fail("ERROR: can not build plane with two equal points.");
        } catch (IllegalArgumentException e) {
        }

        // TC02: Points on the same line
        try {
            Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(2, 0, 0), new Point3D(3, 0, 0));
            fail("ERROR: can not build plane with three points that are found on the same line.");
        } catch (IllegalArgumentException e) {
        }

    }


    /**
     * Test method for {@link Plane#getNormal(Point3D)} }.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: the calculation of the normal to a plane is correct
        Plane plane = new Plane(new Point3D(0, 1, 1), new Point3D(0, 0, 2), new Point3D(0, 0, 3));
        Vector v1 = new Vector(1, 0, 0);
        Vector v3 = new Vector(-1, 0, 0);
        Vector v2 = plane.getNormal(new Point3D(0, 1, 1));
        if (!v1.equals(v2) && !v3.equals(v2))
            fail("ERROR: wrong normal");
    }

    /**
     * Test method for {@link Plane#findIntersections(Ray ray)} }.
     */

    @Test
    void findIntsersections() {
        Plane plane = new Plane(new Point3D(1,1,1), new Vector(0,0,1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray cut the plane (1 point)
        Ray ray1 = new Ray(new Point3D(0, 0, 3), new Vector(0,-1,-1));
        assertEquals(1, plane.findIntersections(ray1).size(),
                "ERROR: The ray should cut the plane in one point");

        assertEquals(new Point3D(0, -2, 1), plane.findIntersections(ray1).get(0),
                "ERROR:wrong point");

        //TC02:Ray does not cut the plane (0 point)
        Ray ray2 = new Ray(new Point3D(0, 0, 3), new Vector(0,1,1));
        assertNull( plane.findIntersections(ray2),
                "ERROR: The ray should not cut the plane");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane
        // TC11: Ray is included in the plane (0 point)
        Ray ray11 = new Ray(new Point3D(2, 3, 1), new Vector(0,1,0));
        assertNull( plane.findIntersections(ray11),
                "ERROR: The ray should not cut the plane");
        // TC12: Ray is not included in the plane (0 point)
        Ray ray12 = new Ray(new Point3D(0, 0, 2), new Vector(0,1,0));
        assertNull( plane.findIntersections(ray12),
                "ERROR: The ray should not cut the plane");

        // **** Group: Ray is orthogonal to the plane

        // TC13: Ray starts before the plane (1 point)
        Ray ray13 = new Ray(new Point3D(0, 0, 3), new Vector(0,0,-1));
        assertEquals(1, plane.findIntersections(ray13).size(),
                "ERROR: The ray should cut the plane in one point");

        assertEquals(new Point3D(0, 0, 1), plane.findIntersections(ray13).get(0),
                "ERROR:wrong point");

        // TC14: Ray starts at the plane (0 point)
        Ray ray14 = new Ray(new Point3D(0, 0, 1), new Vector(0,0,1));
        assertNull( plane.findIntersections(ray14),
                "ERROR: The ray should not cut the plane");

        // TC15: Ray starts after the plane (0 point)
        Ray ray15 = new Ray(new Point3D(0, 0, 3), new Vector(0,0,1));
        assertNull( plane.findIntersections(ray15),
                "ERROR: The ray should not cut the plane");

        // **** Group: Other cases

        // TC16: Ray starts at the plane (but not orthogonal or parallel) (0 point)
        Ray ray16 = new Ray(new Point3D(0, 0, 1), new Vector(0,1,1));
        assertNull( plane.findIntersections(ray16),
                "ERROR: The ray should not cut the plane");

        // TC17: Ray starts at the reference point in the plane (but not orthogonal or parallel) (0 point)
        Ray ray17 = new Ray(new Point3D(1, 1, 1), new Vector(0,1,1));
        assertNull( plane.findIntersections(ray17),
                "ERROR: The ray should not cut the plane");

    }

}