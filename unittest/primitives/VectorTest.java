package primitives;


import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 *
 * @author Naomi and Noam
 */
class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * test zero vector
     */
    @Test
    void testZeroPoint() {
        try {
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void add() {
        assertEquals(v1.add(v2), new Vector(-1, -2, -3), "ERROR: wrong add");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    void subtract() {
        assertEquals(v1.subtract(v2), new Vector(3, 6, 9), "ERROR: wrong subtract");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:  A simple case of multiply vector in scalar
        Vector vscale = v1.scale(-1);
        assertEquals(vscale, new Vector(-1, -2, -3), "ERROR: Multiply vector in Scalar is incorrect");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: dotProduct for 2 vectors is correct
        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");
        // =============== Boundary Values Tests ==================
        //TC11: dotProduct for orthogonal vectors is zero
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */

    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:  A simple case of length Squared calculation
        assertTrue(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */

    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:  A simple case of length calculation
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5), "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */

    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:  vector normalization vs vector length and cross-product
        Vector vCopy = new Vector(v1.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals(vCopy, vCopyNormalize, "ERROR: normalize() function creates a new vector");
        assertTrue(isZero(vCopyNormalize.length() - 1), "ERROR: normalize() result is not a unit vector");
    }

    /**
     * Test method for {@link primitives.Vector#normalized()}.
     */

    @Test
    void normalized() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: normalizated() function create a new vector
        Vector u = v1.normalized();
        assertNotEquals(u, v1, "ERROR: normalizated() function does not create a new vector");
    }
}