package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point3D class
 *
 * @author Naomi and Noam
 */
class Point3DTest {

    Point3D p1 = new Point3D(1, 2, 3);
    Point3D p2 = new Point3D(1, 2, 4);

    /**
     * Test method for {@link primitives.Point3D#subtract(Point3D)}.
     */
    @Test
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of subtract
    void subtract() {
        assertEquals(new Vector(1, 1, 1),new Point3D(2, 3, 4).subtract(p1),
                "ERROR: Point - Point does not work correctly");
    }

    /** 
     * Test method for {@link primitives.Point3D#add(Vector)}.
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of add
        assertEquals(Point3D.ZERO, p1.add(new Vector(-1, -2, -3)),
                "ERROR: Point + Vector does not work correctly");

    }

    /**
     * Test method for {@link primitives.Point3D#distance(Point3D)}.
     * (use also distanceSquared function)
     */
    @Test
    void distance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of distance
        assertEquals(1 , p1.distance(p2), "ERROR: Distance between does not work correctly");
    }

}