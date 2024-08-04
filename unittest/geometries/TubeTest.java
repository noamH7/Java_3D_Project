
package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for geometries.Tube class
 *
 * @author Naomi and Noam
 */
class TubeTest {

    /**
     * Test method for {@link Tube#getNormal(primitives.Point3D)} }.
     */
    @Test
    void getNormal() {
        Tube tube = new Tube(new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1)), 1);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple case of generate normal
        Vector v1 = new Vector(0, 1, 0);
        Vector v2 = tube.getNormal(new Point3D(0, 1, 2));
        assertEquals(v1, v2, "ERROR: wrong normal");

        // =============== Boundary Values Tests ==================

        // TC10: The point is in front of the head of the ray
        Vector v3 = new Vector(0, 1, 0);
        Vector v4 = tube.getNormal(new Point3D(0, 1, 1));
        assertEquals(v3, v4, "ERROR: wrong normal");
    }
    /**
     * Test method for {@link Tube#findIntersections(Ray)}.
     */
    @Test
    void findIntsersections() {
    }
}