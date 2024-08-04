package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 *
 * @author Naomi and Noam
 */
class SphereTest {

    Sphere sphere = new Sphere(1, new Point3D(0, 0, 1));

    /**
     * Test method for {@link Sphere#getNormal(primitives.Point3D)} }.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: the calculation of the normal to a sphere is correct
        Vector v1 = new Vector(0, 0, 1);
        Vector v2 = sphere.getNormal(new Point3D(0, 0, 2));
        assertEquals(v1, v2, "ERROR: wrong normal");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersectionsRay() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        Point3D gp1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D gp2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> exp = List.of(gp1, gp2);
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");
    }

    /**
     * Test method for {@link Sphere#findIntersections(Ray ray)} }.
     */
    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: The ray cut the sphere in two points
        Ray ray1 = new Ray(new Point3D(0, 0.5, 0), new Vector(0, 0, 1));
        Point3D p1 = new Point3D(0, 0.5, 1.8660254037844386);
        Point3D p2 = new Point3D(0, 0.5, 0.1339745962155614);
        List<Point3D> result = sphere.findIntersections(ray1);
        assertEquals(2, result.size(), "ERROR: The ray should cut the sphere in two points");
        if (result.get(0).getZ() < result.get(1).getZ())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "ERROR:wrong points");

        //TC02: The ray coming out from the sphere and cut it in one point
        Ray ray2 = new Ray(new Point3D(0, 0.5, 1), new Vector(0, 0, 1));
        result = sphere.findIntersections(ray2);
        assertEquals(1, result.size(),
                "ERROR: The ray should cut the sphere in one point");
        assertEquals(new Point3D(0, 0.5, 1.8660254037844386), result.get(0),
                "ERROR:wrong point");

        //TC03: The ray start after the sphere
        Ray ray3 = new Ray(new Point3D(0, 0.5, 3), new Vector(0, 0, 1));
        assertNull(sphere.findIntersections(ray3),
                "ERROR: The ray should not cut the sphere");

        //TC04: The ray coming out from the sphere and cut it in one point
        Ray ray4 = new Ray(new Point3D(3, 0, 0), new Vector(0, 0, 1));
        assertNull(sphere.findIntersections(ray4),
                "ERROR: The ray should not cut the sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        Ray ray11 = new Ray(new Point3D(1, 0, 1), new Vector(-1, 0, 1));
        result = sphere.findIntersections(ray11);
        assertEquals(1, sphere.findIntersections(ray11).size(),
                "ERROR: The ray should cut the sphere in one point");
        assertEquals(new Point3D(0, 0, 2), result.get(0),
                "ERROR:wrong point");
        // TC12: Ray starts at sphere and goes outside (0 points)
        Ray ray12 = new Ray(new Point3D(1, 0, 1), new Vector(1, 0, -1));
        assertNull(sphere.findIntersections(ray12),
                "ERROR: The ray should not cut the sphere");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        Ray ray13 = new Ray(new Point3D(0, 0, 3), new Vector(0, 0, -1));
        result = sphere.findIntersections(ray13);
        assertEquals(2, sphere.findIntersections(ray13).size(),
                "ERROR: The ray should cut the sphere in two points");
         p1 = new Point3D(0, 0, 0);
         p2 = new Point3D(0, 0, 2);
        if (result.get(0).getZ() > result.get(1).getZ())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "ERROR:wrong points");
        // TC14: Ray starts at sphere and goes inside (1 points)
        Ray ray14 = new Ray(new Point3D(0, 0, 2), new Vector(0, 0, -1));
        result = sphere.findIntersections(ray14);
        assertEquals(1, sphere.findIntersections(ray14).size(),
                "ERROR: The ray should cut the sphere in one point");
        assertEquals(new Point3D(0, 0, 0), result.get(0),
                "ERROR:wrong point");
        // TC15: Ray starts inside (1 points)
        Ray ray15 = new Ray(new Point3D(0, 0, 1.5), new Vector(0, 0, 1));
        result = sphere.findIntersections(ray15);
        assertEquals(1, sphere.findIntersections(ray15).size(),
                "ERROR: The ray should cut the sphere in one point");
        assertEquals(new Point3D(0, 0, 2), result.get(0),
                "ERROR:wrong point");
        // TC16: Ray starts at the center (1 points)
        Ray ray16 = new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1));
        result = sphere.findIntersections(ray16);
        assertEquals(1, sphere.findIntersections(ray16).size(),
                "ERROR: The ray should cut the sphere in one point");
        assertEquals(new Point3D(0, 0, 2), result.get(0),
                "ERROR:wrong point");
        // TC17: Ray starts at sphere and goes outside (0 points)
        Ray ray17 = new Ray(new Point3D(0, 0, 2), new Vector(0, 0, 1));
        assertNull(sphere.findIntersections(ray17), "ERROR: The ray should not cut the sphere");
        // TC18: Ray starts after sphere (0 points)
        Ray ray18 = new Ray(new Point3D(0, 0, 3), new Vector(0, 0, 1));
        assertNull(sphere.findIntersections(ray18),
                "ERROR: The ray should not cut the sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        Ray ray19 = new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1));
        assertNull(sphere.findIntersections(ray19),
                "ERROR: The ray should not cut the sphere");

        // TC20: Ray starts at the tangent point
        Ray ray20 = new Ray(new Point3D(1, 0, 1), new Vector(0, 0, 1));
        assertNull(sphere.findIntersections(ray20),
                "ERROR: The ray should not cut the sphere");
        // TC21: Ray starts after the tangent point
        Ray ray21 = new Ray(new Point3D(1, 0, 2), new Vector(0, 0, 1));
        assertNull(sphere.findIntersections(ray21),
                "ERROR: The ray should not cut the sphere");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        Ray ray22 = new Ray(new Point3D(2, 0, 1), new Vector(0, 0, 1));
        assertNull(sphere.findIntersections(ray22),
                "ERROR: The ray should not cut the sphere");
    }

}