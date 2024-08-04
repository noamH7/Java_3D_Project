package elements;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * Camera class is the point view to the scene
 *
 * @author Noam and Naomi
 */

public class Camera {
    /**
     * location of camera
     */
    private Point3D _p0;

    /**
     * the direction vector from the camera to the scene
     */
    private Vector _Vto;

    /**
     * the direction vector Up from the camera (orthogonal to Vto)
     */
    private Vector _Vup;

    /**
     * the direction vector right from the camera (orthogonal to Vto and Vup)
     */
    private Vector _Vright;

    /**
     * the width of the view plane
     */
    private double _width;

    /**
     * the height of the view plane
     */
    private double _height;

    /**
     * the distance from camera to view plane
     */
    private double _distance;

    /**
     * the square root of the number of rays in the beam from each pixel
     */
    private int _superSampling = 1;


    /**
     * constructor of camera
     *
     * @param p0  is the location of the camera
     * @param vto is the vector from the camera to the view Plane
     * @param vup is the vector from the camera that parallel to the view plane
     */
    public Camera(Point3D p0, Vector vto, Vector vup) {
        if (!isZero(vto.dotProduct(vup)))
            throw new IllegalArgumentException("Vto and Vup should be orthogonal");
        _p0 = p0;
        _Vto = vto.normalized();
        _Vup = vup.normalized();
        _Vright = _Vto.crossProduct(_Vup);
    }

    /**
     * @return the location of camera
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * @return the vector from the camera to the view Plane
     */
    public Vector getVto() {
        return _Vto;
    }

    /**
     * @return the vector from the camera that parallel to the view plane
     */
    public Vector getVup() {
        return _Vup;
    }

    /**
     * @return the vector that orthogonal to vto and vup
     */
    public Vector getVright() {
        return _Vright;
    }

    /**
     * @return sqrt number of rays in beam
     */
    public int getSuperSampling() {
        return _superSampling;
    }

    /**
     * setter with builder pattern to update view plane's size
     *
     * @param width  of the view plane
     * @param height of the view plane
     * @return the object  for linking
     */
    public Camera setViewPlaneSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }

    /**
     * setter with builder pattern to update the distance between view plane and camera
     *
     * @param distance
     * @return the object for linking
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * setter with builder pattern to update the sqrt number of rays in beam.
     *
     * @param superSampling sqrt number of rays in beam
     * @return the object for linking
     */
    public Camera setSuperSampling(int superSampling) {
        if (superSampling < 1)
            throw new IllegalArgumentException("Multithreading parameter must be 1 or higher");
        _superSampling = superSampling;
        return this;
    }

    /**
     * construct ray or beam from camera through pixel in view plane to scene
     *
     * @param nX number of pixels in row
     * @param nY number of pixels in column
     * @param j  the column of current pixel
     * @param i  the row of current pixel
     * @return ray or beam from camera through pixel in view plane to scene
     */
    public List<Ray> constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D pCenter = _p0.add(_Vto.scale(_distance));// image center
        //Ratio, pixel size:
        double rY = _height / nY;
        double rX = _width / nX;
        double yI = (-(i - (nY - 1) / 2.0)) * rY;
        double xJ = (j - (nX - 1) / 2.0) * rX;
        Point3D pIJ = pCenter;// pixel center (if xJ and yI equal to zero)
        if (!isZero(xJ)) pIJ = pIJ.add(_Vright.scale(xJ));// shifting in columns
        if (!isZero(yI)) pIJ = pIJ.add(_Vup.scale(yI));// shifting in rows
        Vector vIJ = pIJ.subtract(_p0);// the vector from the camera to the pixel center
        Ray rayCenter = new Ray(_p0, vIJ);
        if (_superSampling == 1)//construct one ray
            return List.of(rayCenter);
        return constructBeamThroughPixel(rY, rX, pIJ, rayCenter);
    }

    /**
     * construct beam through specific pixel
     *
     * @param heightP   the height of the pixel
     * @param widthP    the width of the pixel
     * @param pCenter   the center of the pixel
     * @param rayCenter the ray center through the pixel
     * @return beam rays through pixel
     */
    public List<Ray> constructBeamThroughPixel(double heightP, double widthP, Point3D pCenter, Ray rayCenter) {
        LinkedList<Ray> beam = new LinkedList<>();
        beam.add(rayCenter);
        for (int i = 0; i < _superSampling; i++) {// go through all sub-pixels
            for (int j = 0; j < _superSampling; j++) {
                //if the number of sub-pixels is odd then we will not create ray from the center pixel because the center ray already go through it
                if (_superSampling % 2 == 0 || i != 0 || j != 0) {
                    //the size of sub pixel
                    double sY = heightP / _superSampling;
                    double sX = widthP / _superSampling;
                    double yI = 0;
                    double xJ = 0;
                    while (isZero(yI)) {
                        yI = -(Math.random() - (i - (_superSampling - 1) / 2.0) - 0.5) * sY;
                    }
                    while (isZero(xJ)) {
                        xJ = (Math.random() + (j - (_superSampling - 1) / 2.0) - 0.5) * sX;
                    }
                    Point3D pRay = pCenter.add(_Vup.scale(yI)).add(_Vright.scale(xJ));
                    Vector vRay = pRay.subtract(_p0);
                    beam.add(new Ray(_p0, vRay));
                }
            }
        }
        return beam;
    }

    /**
     * shifting the camera
     *
     * @param location new location of camera
     * @return camera (for linking)
     */
    public Camera transLocation(Point3D location) {
        _p0 = location;
        return this;
    }

    /**
     * shifting the camera
     *
     * @param shifting the direction of shifting
     * @return camera (for linking)
     */
    public Camera transLocation(Vector shifting) {
        _p0 = _p0.add(shifting);
        return this;
    }


//    public Camera transRotation(double angelX, double angelY, double angelZ) {
//
//        return this;
//    }


}
