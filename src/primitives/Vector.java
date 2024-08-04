package primitives;

import elements.Camera;

import java.util.Objects;

import static primitives.Point3D.ZERO;

/**
 * a basic vector
 * @author noam and naomi
 */
public class Vector {
    /**
     * end point of the vector
     */
    Point3D _head;

    /**
     * getter
     * @return end point of the vector
     */
    public Point3D getHead() {
        return _head;
    }

    /**
     * ctor that get a point
     * @param head end point of the vector
     * @throws IllegalArgumentException when is given vector 0
     */
    public Vector(Point3D head) {
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("head cannot created as (0,0,0) ");
        }
        _head = head;
    }

    /**
     * ctor that get 3 Coordinates
     * @param x coordinate of vector
     * @param y coordinate of vector
     * @param z coordinate of vector
     * @throws IllegalArgumentException when is given vector 0
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z)  {
        this(x.coord, y.coord, z.coord);
    }

    /**
     * ctor that get 3 numbers
     * @param x value of vector
     * @param y value of vector
     * @param z value of vector
     * @throws IllegalArgumentException when is given vector 0
     */
    public Vector(double x, double y, double z)  {
        this(new Point3D(x, y, z));
    }

    /**
     * Vector addition
     * @param v Vector to addition
     * @return the result addition vector
     */
    public Vector add(Vector v) {
        double x = _head._x.coord + v._head._x.coord;
        double y = _head._y.coord + v._head._y.coord;
        double z = _head._z.coord + v._head._z.coord;
        return new Vector(x, y, z);
    }

    /**
     * Vector subtraction
     * @param v Vector to subtraction
     * @return the result subtraction vector
     */
    public Vector subtract(Vector v) {
        return this.add(v.scale(-1));
    }

    /**
     * product vector with Scalar
     * @param d scalar to product
     * @return the result vector
     */
    public Vector scale(double d)  {
        return new Vector(_head._x.coord * d, _head._y.coord * d, _head._z.coord * d);
    }

    /**
     * cross prodoct
     * @param v vector for product
     * @return vector that orthogonal to the two vectors
     */
    public Vector crossProduct(Vector v) {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;
        double v1 = v._head._x.coord;
        double v2 = v._head._y.coord;
        double v3 = v._head._z.coord;
        return new Vector(u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1);
    }

    /**
     * dot product
     * @param v vector for product
     * @return result of product
     */
    public double dotProduct(Vector v) {
        double x = _head._x.coord * v._head._x.coord;
        double y = _head._y.coord * v._head._y.coord;
        double z = _head._z.coord * v._head._z.coord;
        return x + y + z;
    }


    /**
     * length squared of the vector
     * @return the length squared of the vector
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * length of the vector
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * change the vector to a unit vector
     * @return the normalize vector
     */
    public Vector normalize() {
        double length = this.length();
        _head = new Point3D(_head._x.coord / length, _head._y.coord / length, _head._z.coord / length);
        return this;
    }

    /**
     * doesn't change the vector
     * @return a new normalize vector
     */
    public Vector normalized() {
        return (new Vector(_head)).normalize();
    }

//    public Vector transRotation(double angelX, double angelY, double angelZ){
//        return this;
//    }

    /**
     * equals 2 vectors
     *
     * @param o object to compare
     * @return true if they are equals else return false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }


    /**
     * string of object vector
     * @return string of object
     */
    @Override
    public String toString() {
        return _head.toString();
    }

}
