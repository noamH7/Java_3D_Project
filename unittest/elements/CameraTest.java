package elements;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for elements.Camera class
 * @author Naomi and Noam
 */
class CameraTest {

    Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(10);

    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
     */
    @Test
    public void testConstructRayThroughPixel() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: 3X3 Corner (0,0)
        assertEquals( new Ray(Point3D.ZERO, new Vector(-2, -2, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 0).get(0),"Bad ray");

        // TC02: 4X4 Corner (0,0)
        assertEquals( new Ray(Point3D.ZERO, new Vector(-3, -3, 10)),
                camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 0, 0).get(0),"Bad ray");

        // TC03: 4X4 Side (0,1)
        assertEquals( new Ray(Point3D.ZERO, new Vector(-1, -3, 10)),
                camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 0).get(0),"Bad ray");

        // TC04: 4X4 Inside (1,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-1, -1, 10)),
                camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 1).get(0),"Bad ray");

        // =============== Boundary Values Tests ==================
        // TC11: 3X3 Center (1,1)
        assertEquals( new Ray(Point3D.ZERO, new Vector(0, 0, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 1).get(0),"Bad ray");

        // TC12: 3X3 Center of Upper Side (0,1)
        assertEquals( new Ray(Point3D.ZERO, new Vector(0, -2, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 0).get(0),"Bad ray");

        // TC13: 3X3 Center of Left Side (1,0)
        assertEquals( new Ray(Point3D.ZERO, new Vector(-2, 0, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 1).get(0),"Bad ray");

    }
    /**
     * Test method for
     * {@link elements.Camera#transLocation(Point3D)}
     * {@link elements.Camera#transLocation(Vector)}
     */
    @Test
    public void transLocation(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple location transmission
        camera.transLocation(new Point3D(1,3,2)).transLocation(new Vector(2,2,7));
        assertEquals( new Point3D(3,5,9),camera.getP0() ,"Bad location");
    }

//    /**
//     * Test method for
//     * {@link elements.Camera#transRotation(double, double, double)}
//     */
//    @Test
//    public void transRotation(){
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: simple rotation transmission
//        Camera camera1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(10);
//        camera1.transRotation(90,180,30);
//
//
//        // TC02: rotation transmission of more than 360 degree
//        Camera camera2 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(10);
//
//        // =============== Boundary Values Tests ==================
//        // TC10: full rotation, camera stay put.
//        Camera camera3 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(10);
//
//
//    }
}