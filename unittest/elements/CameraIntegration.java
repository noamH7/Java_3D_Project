package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test for integration of camera and ray-geometry integrations
 */
public class CameraIntegration {
    /**
     * function that check if the number of instruction points that created from the view plane's rays is correct.
     *
     * @param camera
     * @param geometry that near the camera.
     * @param expected number of instruction points.
     */
    void assertCameraIntegrations(Camera camera, Intersectable geometry, int expected) {
        camera.setViewPlaneSize(3, 3).setDistance(1);
        int count = 0;
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j) {
                List<Point3D> points = geometry.findIntersections(camera.constructRayThroughPixel(3, 3, j, i).get(0));
                if (points != null)
                    count += points.size();
            }
        assertEquals(expected, count, "ERROR: the number of instruction points is wrong");
    }

    /**
     * Integration tests  for create ray from the camera to sphere's instruction points
     */
    @Test
    public void cameraRaySphereIntegration() {
        //TC01: One ray from the view plane instruct the sphere.
        Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0));
        Sphere sphere1 = new Sphere(1, new Point3D(0, 0, -3));
        assertCameraIntegrations(cam1, sphere1, 2);

        //TC02: All the rays from the view plane instruct the sphere.
        Camera cam2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
        Sphere sphere2 = new Sphere(2.5, new Point3D(0, 0, -2.5));
        assertCameraIntegrations(cam2, sphere2, 18);

        //TC03: Some of the rays from the view plane instruct the sphere.
        Camera cam3 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
        Sphere sphere3 = new Sphere(2, new Point3D(0, 0, -2));
        assertCameraIntegrations(cam3, sphere3, 10);

        //TC04: The camera inside the sphere.
        Camera cam4 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0));
        Sphere sphere4 = new Sphere(4, new Point3D(0, 0, -3));
        assertCameraIntegrations(cam4, sphere4, 9);

        //TC05: The sphere behind the camera.
        Camera cam5 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0));
        Sphere sphere5 = new Sphere(1, new Point3D(0, 0, 3));
        assertCameraIntegrations(cam5, sphere5, 0);
    }

    /**
     * Integration tests  for create ray from the camera to Triangle's instruction points
     */
    @Test
    public void cameraRayTriangleIntegration() {
        Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0));

        //TC01: one instruct point.
        Triangle triangle1 = new Triangle(new Point3D(0, 1, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
        assertCameraIntegrations(cam, triangle1, 1);

        //TC02: two instruction points.
        Triangle triangle2 = new Triangle(new Point3D(0, 20, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
        assertCameraIntegrations(cam, triangle2, 2);

        //TC03: big triangle- all the rays instruct the triangle.
        Triangle triangle3 = new Triangle(new Point3D(0, 20, -2), new Point3D(50, -30, -2), new Point3D(-50, -30, -2));
        assertCameraIntegrations(cam, triangle3, 9);

        //TC04: triangle behind the camera.
        Triangle triangle4 = new Triangle(new Point3D(0, 20, 2), new Point3D(50, -30, 2), new Point3D(-50, -30, 2));
        assertCameraIntegrations(cam, triangle4, 0);
    }

    /**
     * Integration tests  for create ray from the camera to plane's instruction points
     */
    @Test
    public void cameraRayPlaneIntegration() {
        Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0));

        //TC01: plane parallel to camera- all the rays instruct the plane.
        Plane plane1 = new Plane(new Point3D(0, 0, -6), new Vector(0, 0, 1));
        assertCameraIntegrations(cam, plane1, 9);

        //TC02: plane is little sloping- all the rays instruct the plane.
        Plane plane2 = new Plane(new Point3D(0, 0, -6), new Point3D(-6, 6, -6), new Point3D(-6, -6, -6));
        assertCameraIntegrations(cam, plane2, 9);

        //TC03: plane is  very sloping- some of the rays instruct the plane.
        Plane plane3 = new Plane(new Point3D(0, 0, -6), new Vector(0, -1, 1));
        assertCameraIntegrations(cam, plane3, 6);

        //TC04: plane parallel to view plane behind camera.
        Plane plane4 = new Plane(new Point3D(0, 0, 6), new Vector(0, 0, 1));
        assertCameraIntegrations(cam, plane4, 0);

        //TC05: plane orthogonal to camera.
        Plane plane5 = new Plane(new Point3D(0, 5, 0), new Vector(0, 1, 0));
        assertCameraIntegrations(cam, plane5, 3);


    }
}


